package me.yadmand.instaonmvvm.data

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EmpDao (context: FragmentActivity?): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    private val empList = mutableListOf<EmpModelClass>()
    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    private val emps = MutableLiveData<List<EmpModelClass>>()

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_CONTACTS = "EmployeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
    }
    init {
        emps.value=empList
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + EmpDao.TABLE_CONTACTS + "("
                + EmpDao.KEY_ID + " INTEGER PRIMARY KEY," + EmpDao.KEY_NAME + " TEXT,"
                + EmpDao.KEY_EMAIL + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + EmpDao.TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addEmployee(emp: EmpModelClass):Long{
        empList.add(emp)
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(EmpDao.KEY_ID, emp.userId)
        contentValues.put(EmpDao.KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(EmpDao.KEY_EMAIL,emp.userEmail ) // EmpModelClass Phone
        // Inserting Row
        val success = db.insert(EmpDao.TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        emps.value= empList
        return success
    }
    //method to read data
    fun viewEmployee(): LiveData<EmpModelClass> {

        val selectQuery = "SELECT  * FROM ${EmpDao.TABLE_CONTACTS}"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return emps as LiveData<EmpModelClass>
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return emps as LiveData<EmpModelClass>
    }
    //method to update data
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(EmpDao.KEY_ID, emp.userId)
        contentValues.put(EmpDao.KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(EmpDao.KEY_EMAIL,emp.userEmail ) // EmpModelClass Email

        // Updating Row
        val success = db.update(EmpDao.TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(EmpDao.KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(EmpDao.TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}

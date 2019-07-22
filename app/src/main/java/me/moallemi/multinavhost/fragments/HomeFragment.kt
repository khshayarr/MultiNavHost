package me.moallemi.multinavhost.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import me.moallemi.multinavhost.R
import me.moallemi.multinavhost.model.EmpModelClass
import me.moallemi.multinavhost.util.DatabaseHandler
import me.moallemi.multinavhost.util.MyListAdapter

import androidx.recyclerview.widget.RecyclerView



class HomeFragment : BaseFragment() {
    var mListRecyclerView: RecyclerView? = null
    var mAdapter: ListAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        // only create and set a new adapter if there isn't already one  rv_animal_list.layoutManager = LinearLayoutManager(this)
        //
        //        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        ////        rv_animal_list.layoutManager = GridLayoutManager(this, 2)
        //
        //        // Access the RecyclerView Adapter and load the data into it
        //        rv_animal_list.adapter = AnimalAdapter(animals, this)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListRecyclerView = view.findViewById(R.id.list_recycler_view);
        mListRecyclerView!!.setLayoutManager(LinearLayoutManager(getActivity()));
        saverecord.setOnClickListener(){
            saveRecord(view)

        }
        viewrecord.setOnClickListener(){
           mListRecyclerView?.adapter= viewRecord(view)

        }
        deleterecord.setOnClickListener(){
            deleteRecord(view)
        }
        updaterecord.setOnClickListener(){
            updateRecord(view)
        }
        /*
        buttonNextPage.setOnClickListener {
            val action = NavigationGraphMainDirections.actionGlobalPageFragment(1, "HomeFragment")
            view.findNavController().navigate(action)
        }*/
    }

    //method for saving records in database
    fun saveRecord(view: View) {

        val id = u_id.text.toString()
        val name = u_name.text.toString()
        val email = u_email.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(getActivity())
        if (id.trim() != "" && name.trim() != "" && email.trim() != "") {
            val status = databaseHandler.addEmployee(EmpModelClass(Integer.parseInt(id), name, email))
            if (status > -1) {
                Toast.makeText(getActivity(), "record save", Toast.LENGTH_LONG).show()
                u_id.text.clear()
                u_name.text.clear()
                u_email.text.clear()
            }
        } else {
            Toast.makeText(getActivity(), "id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }

    }

    //method for read records from database in ListView
    fun  viewRecord (view: View) :MyListAdapter {
        //creating the instance of DatabaseHandler class
        val context : FragmentActivity? = getActivity()
        val databaseHandler: DatabaseHandler = DatabaseHandler(context)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size) { "0" }
        val empArrayName = Array<String>(emp.size) { "null" }
        val empArrayEmail = Array<String>(emp.size) { "null" }
        var index = 0
        for (e in emp) {
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            index++
        }
        //creating custom ArrayAdapter
       /* val myListAdapter = MyListAdapter(this, empArrayId, empArrayName, empArrayEmail)
        list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(myListAdapter)
        }*/
        val myListAdapter = MyListAdapter(getActivity()!!, empArrayId, empArrayName, empArrayEmail)

        return myListAdapter
    }

    //method for updating records based on user id
    fun updateRecord(view: View) {
        val dialogBuilder = AlertDialog.Builder(getActivity()!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            //creating the instance of DatabaseHandler class
            val context: FragmentActivity? = getActivity()
            val databaseHandler: DatabaseHandler = DatabaseHandler(context)
            if (updateId.trim() != "" && updateName.trim() != "" && updateEmail.trim() != "") {
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId), updateName, updateEmail))
                if (status > -1) {
                    Toast.makeText(getActivity(), "record update", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(getActivity(), "id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    //method for deleting records based on id
    fun deleteRecord(view: View) {
        val context: Context? = getActivity()
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler = DatabaseHandler(getActivity()!!)
            if (deleteId.trim() != "") {
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId), "", ""))
                if (status > -1) {
                    Toast.makeText(getActivity(), "record deleted", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(getActivity(), "id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }


}
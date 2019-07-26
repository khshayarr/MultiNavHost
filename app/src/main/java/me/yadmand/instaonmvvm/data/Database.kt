package me.yadmand.instaonmvvm.data

import android.content.Context
import androidx.fragment.app.FragmentActivity

class Database (context: FragmentActivity) {

    // All the DAOs go here!
    var empDao = EmpDao(context)
        private set

    companion object {
        // @Volatile - Writes to this property are immediately visible to other threads
        @Volatile private var instance: Database? = null

        // The only way to get hold of the Database object
        fun getInstance(context: FragmentActivity) =
        // Already instantiated? - return the instance
                // Otherwise instantiate in a thread-safe manner
                instance ?: synchronized(this) {
                    // If it's still not instantiated, finally create an object
                    // also set the "instance" property to be the currently created one
                    instance ?: Database(context).also { instance = it }
                }
    }
}
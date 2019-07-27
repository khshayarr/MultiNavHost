package me.yadmand.instaonmvvm.employee

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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import me.yadmand.instaonmvvm.R
import androidx.lifecycle.ViewModelProviders
import me.yadmand.instaonmvvm.util.MyListAdapter

import androidx.recyclerview.widget.RecyclerView
import me.yadmand.instaonmvvm.data.EmpModelClass
import me.yadmand.instaonmvvm.util.Injector


class HomeFragment : BaseFragment() {
    var mListRecyclerView: RecyclerView? = null
    var mAdapter: ListAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
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
        val factory = Injector.provideEmpViewModelFactory(getActivity()!!)
        val viewModel = ViewModelProviders.of(this, factory)
                .get(EmpViewModel::class.java)
        val id = u_id.text.toString()
        val name = u_name.text.toString()
        val email = u_email.text.toString()
        if (id.trim() != "" && name.trim() != "" && email.trim() != "") {
            val status = viewModel.addEmp(EmpModelClass(Integer.parseInt(id), name, email))
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
        val factory = Injector.provideEmpViewModelFactory(getActivity()!!)
        val viewModel = ViewModelProviders.of(this, factory)
                .get(EmpViewModel::class.java)
        val empArrayId = arrayListOf<String>()
        val empArrayName = arrayListOf<String>()
        val empArrayEmail = arrayListOf<String>()
        viewModel.getEmp().observe(this, Observer { emps ->
            emps.forEach { emp ->
                empArrayId.add(emp.userId.toString())
                empArrayName.add(emp.userName)
                empArrayEmail.add(emp.userEmail)
            }
        })
        val myListAdapter = MyListAdapter(getActivity()!!, empArrayId, empArrayName, empArrayEmail)

        return myListAdapter
    }

    //method for updating records based on user id
    fun updateRecord(view: View) {
        val factory = Injector.provideEmpViewModelFactory(getActivity()!!)
        val viewModel = ViewModelProviders.of(this, factory)
                .get(EmpViewModel::class.java)
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

            if (updateId.trim() != "" && updateName.trim() != "" && updateEmail.trim() != "") {
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = viewModel.updateEmp(EmpModelClass(Integer.parseInt(updateId), updateName, updateEmail))
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
        val factory = Injector.provideEmpViewModelFactory(getActivity()!!)
        val viewModel = ViewModelProviders.of(this, factory)
                .get(EmpViewModel::class.java)
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

            if (deleteId.trim() != "") {
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = viewModel.deleteEmp(EmpModelClass(Integer.parseInt(deleteId), "", ""))
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
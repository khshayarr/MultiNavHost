package me.moallemi.multinavhost.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.moallemi.multinavhost.R
import me.moallemi.multinavhost.fragments.BaseViewHolder

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val email: Array<String>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return id.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        val v = LayoutInflater.from(context).inflate(R.layout.custom_list,
                parent, false)
        return v;
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(myAndroidOsListName: String) {
            val inflater = context.layoutInflater
            val rowView = inflater.inflate(R.layout.custom_list, null, true)

            val idText = rowView.findViewById(R.id.textViewId) as TextView
            val nameText = rowView.findViewById(R.id.textViewName) as TextView
            val emailText = rowView.findViewById(R.id.textViewEmail) as TextView

            idText.text = "Id: ${itemView.id[position]}"
            nameText.text = "Name: ${name[position]}"
            emailText.text = "Email: ${email[position]}"
        }
    }


}
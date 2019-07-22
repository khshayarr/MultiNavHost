package me.moallemi.multinavhost.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.moallemi.multinavhost.R

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val email: Array<String>)
    : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true) //To change body of created functions use File | Settings | File Templates.
        holder?.emailText?.text=email[position]
        holder?.nameText?.text=name[position]
        holder?.idText?.text=id[position]
    }

    override fun getItemCount(): Int {
        return id.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.custom_list,
                parent, false)
        return ViewHolder(v);
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val idText = itemView.findViewById(R.id.textViewId) as TextView
            val nameText = itemView.findViewById(R.id.textViewName) as TextView
            val emailText = itemView.findViewById(R.id.textViewEmail) as TextView
    }


}
package me.yadmand.instaonmvvm.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.yadmand.instaonmvvm.R

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val email: Array<String>)
    : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true) //To change body of created functions use File | Settings | File Templates.
        holder.isRecyclable
        holder.bindItems(id[position],email[position],name[position])
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
        fun bindItems(id : String,email: String,name :String) {
            val idText = itemView.findViewById(R.id.textViewId) as TextView
            val nameText = itemView.findViewById(R.id.textViewName) as TextView
            val emailText = itemView.findViewById(R.id.textViewEmail) as TextView
            idText.text=id
            nameText.text=name
            emailText.text=email

        }
    }


}
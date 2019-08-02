package me.yadmand.instaonmvvm.util

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.yadmand.instaonmvvm.R
import me.yadmand.instaonmvvm.data.EmpModelClass
import me.yadmand.instaonmvvm.employee.HomeFragment

class MyListAdapter(private val context: Activity, private val id: ArrayList<String>, private val name: ArrayList<String>, private val email: ArrayList<String>)
    : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true) //To change body of created functions use File | Settings | File Templates.
        holder.isRecyclable
        holder.bindItems(id[position], email[position], name[position])

    }
    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }

    override fun getItemCount(): Int {
        return id.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.custom_list,
                parent, false)

        return ViewHolder(v)
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val idHolder = itemView.findViewById(R.id.textViewId) as TextView
        fun bindItems(id: String, email: String, name: String) {
            val idText = itemView.findViewById(R.id.textViewId) as TextView
            val nameText = itemView.findViewById(R.id.textViewName) as TextView
            val emailText = itemView.findViewById(R.id.textViewEmail) as TextView
            idText.text = id
            nameText.text = name
            emailText.text = email



        }
        override fun onClick(v: View) {

            mClickListener.onClick(adapterPosition, v)
        }

        init {
            itemView.setOnClickListener(this)
        }

    }


}
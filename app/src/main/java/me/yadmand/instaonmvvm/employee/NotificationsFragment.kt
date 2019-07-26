package me.yadmand.instaonmvvm.employee

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notifications.*
import me.yadmand.instaonmvvm.R

class NotificationsFragment : BaseFragment() {
    private val sharedPrefFile = "kotlinsharedpreference"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences = getActivity()!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        save.setOnClickListener({
            val id:Int = Integer.parseInt(editId.text.toString())
            val name:String = editName.text.toString()
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putInt("id_key",id)
            editor.putString("name_key",name)
            editor.apply()
        })
        viewo.setOnClickListener {
            val sharedIdValue = sharedPreferences.getInt("id_key",0)
            val sharedNameValue = sharedPreferences.getString("name_key","defaultname")
            if(sharedIdValue.equals(0) && sharedNameValue.equals("defaultname")){
                textViewShowName.setText("default name: ${sharedNameValue}").toString()
                textViewShowId.setText("default id: ${sharedIdValue.toString()}")
            }else{
                textViewShowName.setText(sharedNameValue).toString()
                textViewShowId.setText(sharedIdValue.toString())
            }

        }
        clear.setOnClickListener(View.OnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            editName.setText("").toString()
            editId.setText("".toString())
        })
    }
}
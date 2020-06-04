package com.aditya.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Simple : Fragment(),CommonInterface {


    @SuppressLint("HardwareIds")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_simple, container, false)

        val float=view.findViewById(R.id.addSimple) as FloatingActionButton
        val id:String = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)

        val recyclerView=view.findViewById(R.id.recSimpleInterest) as RecyclerView
        getData(recyclerView = recyclerView,str = "SimpleInterestData$id",context = context,layout = "simple")
        float.setOnClickListener {
            val intent= Intent(context,SimpleInterest::class.java)
            startActivity(intent)
        }
        return view
    }
}


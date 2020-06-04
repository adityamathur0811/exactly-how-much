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

class Compound : Fragment(),CommonInterface {

    @SuppressLint("HardwareIds")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_compound, container, false)
        val floating=view.findViewById(R.id.addCompound) as FloatingActionButton
        val id:String = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)

        val recyclerView=view.findViewById(R.id.recCompoundInterest) as RecyclerView
        getData(recyclerView = recyclerView,str = "CompoundInterestData$id",context =  context,layout = "compound")
        floating.setOnClickListener {
            val intent= Intent(context,CompoundInterest::class.java)
            startActivity(intent)
        }
        return view
    }

    }


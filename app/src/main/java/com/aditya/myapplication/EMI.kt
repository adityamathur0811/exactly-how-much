package com.aditya.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception


class EMI : Fragment() {

    @SuppressLint("HardwareIds")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_e_m_i, container, false)

        val floating=view.findViewById(R.id.addEMI) as FloatingActionButton
        val id:String = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)

        val recyclerView=view.findViewById(R.id.recEMI) as RecyclerView
        forPerMonthEmi(recyclerView = recyclerView,str = "PerMonthEmi$id")
        floating.setOnClickListener {
            val intent= Intent(context,PerMonthEmi::class.java)
            startActivity(intent)
        }
        return view
    }
    private fun forPerMonthEmi(
        recyclerView:RecyclerView,
        str:String
    )
    {
        recyclerView.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(str)

        try {


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val arrayList=ArrayList<Data>()
                for (snapshot in dataSnapshot.children) {
                    val data: Data? = snapshot.getValue(Data::class.java)
                    if (data != null) {
                        arrayList.add(data)

                    recyclerView.adapter = EmiAdapter( arrayList )
                }}
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        }catch (e:Exception){}
    }

}
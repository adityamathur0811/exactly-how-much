package com.aditya.myapplication

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

interface CommonInterface {
    fun noOfYears(month: Int): Int {
        var s = 1
        var y = 1
        var l = 12
        var temp = 1

        for (i in 1..100) {
            if (month in s..l) {
                y = i
            }
            s += 12
            l += 12
            temp++
        }
        return y
    }

    fun getData(
        recyclerView: RecyclerView,
        str: String,
        layout:String,
        context: Context?
    ) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(str)

        try {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val arrayList = ArrayList<Data>()
                    for (snapshot in dataSnapshot.children) {
                        val data: Data? = snapshot.getValue(Data::class.java)
                        if (data != null) {
                            arrayList.add(data)

                        recyclerView.adapter = MyAdapter(arrayList, layout)
                    }}
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
        }
    }
}

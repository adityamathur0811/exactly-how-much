package com.aditya.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MyAdapter(private val dataList: ArrayList<Data>, val name: String) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var v :View?=null

        when (name) {
            "simple" -> v = LayoutInflater.from(parent.context)
                .inflate(R.layout.rec_simple_interest, parent, false)
            "compound" -> v = LayoutInflater.from(parent.context)
                .inflate(R.layout.rec_compound_interest, parent, false)

        }
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.setData(dataList[(dataList.size - 1) - position])

    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        @SuppressLint("SetTextI18n")
        fun setData(data: Data) {
            val textView1 = itemView.findViewById(R.id.recAmount) as TextView
            val textView2 = itemView.findViewById(R.id.recRate) as TextView
            val textView3 = itemView.findViewById(R.id.recTime) as TextView
            val textView4 = itemView.findViewById(R.id.recInt) as TextView
            val textView5 = itemView.findViewById(R.id.recFinalAmount) as TextView
            textView1.text = "${data.amount}"
            textView2.text = "${data.rate} %"
            textView3.text = "${data.years}"
            val interest: String = data.interest.toString()
            val finalAmount = data.final.toString()
            try {


                val separator = "."
                val indexInt = interest.indexOf(separator)
                val indexFinal = finalAmount.indexOf(separator)
                if (interest.length >= indexInt.plus(3)) {
                    val amtInt = interest.subSequence(0, indexInt.plus(3))
                    textView4.text = amtInt
                } else {
                    val amtInt = interest.subSequence(0, indexInt.plus(2))
                    textView4.text = amtInt
                }
                if (interest.length >= indexInt.plus(3)) {
                    val amtFinal = finalAmount.subSequence(0, indexFinal.plus(3))
                    textView5.text = amtFinal

                } else {
                    val amtFinal = finalAmount.subSequence(0, indexFinal.plus(2))
                    textView5.text = amtFinal

                }

            }catch (e:Exception){}

            }


        }

    }


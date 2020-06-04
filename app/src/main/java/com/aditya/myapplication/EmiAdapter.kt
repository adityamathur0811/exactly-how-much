package com.aditya.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class EmiAdapter(private val dataList: ArrayList<Data>) :
    RecyclerView.Adapter<EmiAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v :View=LayoutInflater.from(parent.context).inflate(R.layout.rec_layout_emi, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(dataList[(dataList.size - 1) - position])


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun setData(data: Data) {
            val textView1 = itemView.findViewById(R.id.recAmountEMI) as TextView
            val textView2 = itemView.findViewById(R.id.recRateEMI) as TextView
            val textView3 = itemView.findViewById(R.id.recTimeEMI) as TextView
            val textView4 = itemView.findViewById(R.id.recFinalAmountEMI) as TextView

            textView1.text = "${data.amount}"
            textView2.text = "${data.rate} %"
            textView3.text = "${data.years.toInt()}"
            val finalAmount = data.final.toString()
            try {


            val separator="."
            val indexInt=finalAmount.indexOf(separator)
            if (finalAmount.length>=indexInt.plus(3)) {
                val amtInt = finalAmount.subSequence(0, indexInt.plus(3))
                textView4.text = amtInt
            }
            else
            {
                val amtInt = finalAmount.subSequence(0, indexInt.plus(2))
                textView4.text = amtInt
            }



        }catch (e:Exception){}

    }}

}
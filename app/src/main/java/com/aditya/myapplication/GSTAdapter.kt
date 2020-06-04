package com.aditya.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class GSTAdapter(private val dataList: ArrayList<Data>) :
    RecyclerView.Adapter<GSTAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v :View=LayoutInflater.from(parent.context).inflate(R.layout.rec_gst, parent, false)
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
            val textView1 = itemView.findViewById(R.id.recProductGST) as TextView
            val textView2 = itemView.findViewById(R.id.recAmountGST) as TextView
            val textView3 = itemView.findViewById(R.id.recRateGSt) as TextView
            val textView4 = itemView.findViewById(R.id.recIntGSt) as TextView
            val textView5 = itemView.findViewById(R.id.recFinalAmountGST) as TextView

            textView1.text = data.name
            textView2.text = "${data.amount}"
            textView3.text = "${data.rate}%"
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
package com.aditya.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Secure
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_g_s_t.*

class NewGST : AppCompatActivity() {

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_g_s_t)
        val id: String = Secure.getString(
            contentResolver,
            Secure.ANDROID_ID
        )
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("GST$id")
        val slabs = resources.getStringArray(R.array.GST)
        var pos = 0
        var rate = 0.0
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, slabs)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                pos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        GstResult.setOnClickListener {
            try {


                when (pos) {
                    0 -> rate = 0.0
                    1 -> rate = 0.25
                    2 -> rate = 5.toDouble()
                    3 -> rate = 12.toDouble()
                    4 -> rate = 18.toDouble()
                    5 -> rate = 28.toDouble()
                }
                val product = productName.text.toString()
                val p = productPrice.text.toString()

                when {
                    product.isBlank() -> {
                        productName.error = "please enter product name"

                    }
                    p.isBlank() -> {
                        productPrice.error = "please enter product price"

                    }
                    else -> {
                        val price = p.toDouble()
                        val gSTAmount = calculateGST(rate = rate, price = price)

                        val finalGSTAmount = gSTAmount.plus(price)

                        val i = gSTAmount.toString()
                        val f = finalGSTAmount.toString()
                        try {


                            val separator = "."
                            val indexInt = i.indexOf(separator)
                            val indexFinal = f.indexOf(separator)
                            if (i.length >= indexInt.plus(3)) {
                                val amtInt = i.subSequence(0, indexInt.plus(3))
                                gstAmount.text = "$amtInt"
                            } else {
                                val amtInt = i.subSequence(0, indexInt.plus(2))
                                gstAmount.text = "$amtInt"
                            }
                            if (f.length >= indexFinal.plus(3)) {
                                val amtFinal = f.subSequence(0, indexFinal.plus(3))
                                gstFinalAmount.text = "$amtFinal"
                            } else {
                                val amtFinal = f.subSequence(0, indexFinal.plus(2))
                                gstFinalAmount.text = "$amtFinal"
                            }

                        } catch (e: Exception) {
                        }
                        val data = Data(
                            interest = gSTAmount,
                            name = product,
                            final = finalGSTAmount,
                            rate = rate,
                            years = 0.0,
                            amount = price
                        )
                        val childId: String? = myRef.push().key

                        if (childId != null) {
                            myRef.child(childId).setValue(data)

                        }

                    }
                }
            }catch (e:Exception){}
        }

    }
    private fun calculateGST(rate: Double, price: Double) = (rate.times(price)) / 100
}

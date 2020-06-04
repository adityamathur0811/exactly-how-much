package com.aditya.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_simple_inrerest.*

class SimpleInterest : AppCompatActivity(), CommonInterface {


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_inrerest)

        val id: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("SimpleInterestData$id")
        result.setOnClickListener {
            try {


                val amount1 = eAmount.text.toString()
                val rate1 = rate.text.toString()
                val month = years.text.toString()


                when {
                    amount1.isBlank() -> {
                        eAmount.error = "please enter amount"
                    }
                    rate1.isBlank() -> {
                        rate.error = "please enter interest rate"
                    }
                    month.isBlank() -> {
                        years.error = "please enter no of months"
                    }
                    else -> {
                        val a = amount1.toDouble()
                        val r = rate1.toDouble()
                        val m = month.toInt()
                        val y = noOfYears(m)
                        val yearInter = finalAmount(a = a, r = r, y = y)
                        val inter = yearInter.times(m) / (y.times(12))
                        val final = (a.plus(inter))

                        val i = inter.toString()
                        val f = final.toString()
                        try {


                            val separator = "."
                            val indexInt = i.indexOf(separator)
                            val indexFinal = f.indexOf(separator)
                            if (i.length >= indexInt.plus(3)) {
                                val amtInt = i.subSequence(0, indexInt.plus(3))
                                interest.text = "$amtInt"
                            } else {
                                val amtInt = i.subSequence(0, indexInt.plus(2))
                                interest.text = "$amtInt"
                            }
                            if (f.length >= indexFinal.plus(3)) {
                                val amtFinal = f.subSequence(0, indexFinal.plus(3))
                                finalAmount.text = "$amtFinal"

                            } else {
                                val amtFinal = f.subSequence(0, indexFinal.plus(2))
                                finalAmount.text = "$amtFinal"

                            }


                        } catch (e: Exception) {
                        }
                        val data = Data(
                            amount = a,
                            years = m.toDouble(),
                            rate = r,
                            final = final,
                            interest = inter,
                            name = ""
                        )
                        val childId
                                : String
                        ? = myRef.push().key

                        if (childId != null) {
                            myRef.child(childId).setValue(data)
                        }
                    }

                }
            }catch (e:Exception){}
        }

        }


    private fun finalAmount(a: Double, r: Double, y: Int): Double = (a.times(r).times(y)) / 100


    private operator fun Int.invoke(): Boolean {
        return true
    }

}





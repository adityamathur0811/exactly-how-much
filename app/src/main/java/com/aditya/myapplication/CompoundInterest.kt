package com.aditya.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_compound_interest.*
import java.lang.Exception
import kotlin.math.pow

class CompoundInterest : AppCompatActivity(),CommonInterface {

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compound_interest)
        val database = FirebaseDatabase.getInstance()
        val id: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val myRef = database.getReference("CompoundInterestData$id")
        cResult.setOnClickListener {
            try {


                val amount1 = cAmount.text.toString()
                val rate1 = cRate.text.toString()
                val times = cYears.text.toString()
                when {
                    amount1.isBlank() -> {
                        cAmount.error = "please enter amount"
                    }
                    rate1.isBlank() -> {
                        cRate.error = "please enter interest rate"
                    }
                    times.isBlank() -> {
                        cYears.error = "please enter no of years"
                    }
                    else -> {

                        val a = amount1.toDouble()
                        val r = rate1.toDouble()
                        val t = times.toDouble()
                        val money = finalAmount(amount = a, rate = r, time = t)
                        val final = money.plus(a)
                        val i = money.toString()
                        val f = final.toString()
                        try {

                            val separator = "."
                            val indexInt = i.indexOf(separator)
                            val indexFinal = f.indexOf(separator)
                            if (i.length >= indexInt.plus(3)) {
                                val amtInt = i.subSequence(0, indexInt.plus(3))
                                cInterest.text = "$amtInt"
                            } else {
                                val amtInt = i.subSequence(0, indexInt.plus(2))
                                cInterest.text = "$amtInt"
                            }
                            if (f.length >= indexFinal.plus(3)) {
                                val amtFinal = f.subSequence(0, indexFinal.plus(3))
                                cFinalAmount.text = "$amtFinal"

                            } else {
                                val amtFinal = f.subSequence(0, indexFinal.plus(2))
                                cFinalAmount.text = "$amtFinal"
                            }
                        } catch (e: Exception) {
                        }
                        val data = Data(
                            amount = a,
                            rate = r,
                            years = t,
                            interest = money,
                            final = final,
                            name = ""
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
    private fun finalAmount(amount: Double, rate: Double, time: Double): Double {
        return (amount* (1 + rate / 100).pow(time))

    }
}
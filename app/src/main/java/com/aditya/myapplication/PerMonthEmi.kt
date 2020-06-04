package com.aditya.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Secure
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_per_month_emi.*
import java.lang.Exception
import kotlin.math.pow

class PerMonthEmi : AppCompatActivity() {

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_per_month_emi)
        val database = FirebaseDatabase.getInstance()
        val id:String = Secure.getString(
            contentResolver,
            Secure.ANDROID_ID)
        val myRef = database.getReference("PerMonthEmi$id")
        getPerMonthEmi.setOnClickListener {
            try {


                val amount1 = EmiAmount.text.toString()
                val rate1 = Emirate.text.toString()
                val year1 = EmiYears.text.toString()

                when {
                    amount1.isBlank() -> {
                        EmiAmount.error = "please enter amount"
                    }
                    rate1.isBlank() -> {
                        Emirate.error = "please enter interest rate"
                    }
                    year1.isBlank() -> {
                        EmiYears.error = "please enter no of year"
                    }
                    else -> {
                        try {
                            val a = amount1.toDouble()
                            val r = rate1.toDouble()
                            val y = year1.toDouble()
                            val emi = calculateEmi(amount = a, rate = r, month = y)
                            val temp = emi.toString()
                            try {


                                val separator = "."
                                val indexInt = temp.indexOf(separator)
                                if (temp.length >= indexInt.plus(3)) {
                                    val amtInt = temp.subSequence(0, indexInt.plus(3))
                                    EmiFinalAmount.text = "$amtInt"

                                } else {
                                    val amtInt = temp.subSequence(0, indexInt.plus(2))
                                    EmiFinalAmount.text = "$amtInt"

                                }
                            } catch (e: Exception) {
                            }
                            val data = Data(
                                interest = 0.0,
                                amount = a,
                                rate = r,
                                years = y,
                                final = emi,
                                name = ""
                            )
                            val childId: String? = myRef.push().key
                            if (childId != null) {
                                myRef.child(childId).setValue(data)
                            }
                        } catch (e: Exception) {
                        }
                    }
                }
            }catch (e:Exception){}
        }
    }
    private fun calculateEmi(amount:Double,rate:Double,month:Double):Double
    {
        val r=rate.div(12.times(100))
        val t=month.times(12)
        return (amount*r* (1 + r).pow(t))/((1 + r).pow(t) -1)

    }
}

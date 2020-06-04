@file:Suppress("DEPRECATION")

package com.aditya.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottom: BottomNavigationView = findViewById(R.id.bottomNavBar)
        bottom.setOnNavigationItemSelectedListener(navigation)
        setFragment(fragment = GstFragment(),frameId = R.id.fragment)
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if(networkInfo==null)
        {
            Toast.makeText(this,"please unable internet connection to save and retrieve your data",Toast.LENGTH_LONG).show()
        }

    }

    private val navigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.EMI -> {
                setFragment(fragment = EMI(),frameId = R.id.fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.gstSlabMenu -> {
                setFragment(fragment = GstFragment(),frameId = R.id.fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.compound -> {
                setFragment(fragment = Compound(),frameId = R.id.fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.simple -> {
               setFragment(fragment = Simple(),frameId = R.id.fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    private fun AppCompatActivity.setFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.fragmentTransaction { replace(frameId, fragment) } }

    private inline fun FragmentManager.fragmentTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit() }



}

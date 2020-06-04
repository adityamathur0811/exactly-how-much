package com.aditya.myapplication

data class Data(
    var amount: Double,
    var rate: Double,
    var years: Double,
    var interest: Double,
    var final: Double,
    var name:String )
{
    constructor() : this(0.0,0.0,0.0,0.0,0.0,"")


}
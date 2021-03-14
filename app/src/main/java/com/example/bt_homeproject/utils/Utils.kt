package com.example.bt_homeproject.utils

import java.text.SimpleDateFormat
import java.util.*


object Utils {

    fun getDayAndMonth(date: String):String{
        val separatedDate:ArrayList<String> = date.split('-') as ArrayList<String>
        val year = separatedDate[0]
        val month = separatedDate[1]
        val day = "${separatedDate[2][0]}${separatedDate[2][1]}"
        return "$day/$month"
    }

    fun getCurrentTimePeriod():String{                  // Day or Night
        val calendar:Calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        return if(hour in 8..19){
            "Day"
        }else if(hour in 20..25 && hour in 0..8){
            "Night"
        }else{
            "Day"
        }
    }

    fun getDayName(strDate: String):String{
        val tempList:ArrayList<String> = strDate.split('T') as ArrayList<String>
        val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd")
        val date:Date = simpleDateFormat.parse(tempList[0])
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayListEng: Array<String> = arrayOf("PAZAR", "PAZARTESİ", "SALI", "ÇARŞAMBA", "PERŞEMBE", "CUMA", "CUMARTESİ")
        return dayListEng[calendar.get(Calendar.DAY_OF_WEEK) - 1]
    }

    fun fahrenheitToCelsius(f: Int):Int{
        val c = (f-32)/1.8
        return c.toInt()
    }

    fun getIconName(iconId: Int):String{
        when(iconId){
            1 -> {
                return "ic_1"
            }
            2 -> {
                return "ic_2"
            }
            3 -> {
                return "ic_3"
            }
            4 -> {
                return "ic_4"
            }
            5 -> {
                return "ic_5"
            }
            6 -> {
                return "ic_6"
            }
            7 -> {
                return "ic_7"
            }
            8 -> {
                return "ic_8"
            }
            11 -> {
                return "ic_11"
            }
            12 -> {
                return "ic_12"
            }
            13 -> {
                return "ic_13"
            }
            14 -> {
                return "ic_14"
            }
            15 -> {
                return "ic_15"
            }
            16 -> {
                return "ic_16"
            }
            17 -> {
                return "ic_17"
            }
            18 -> {
                return "ic_18"
            }
            19 -> {
                return "ic_19"
            }
            20 -> {
                return "ic_20"
            }
            21 -> {
                return "ic_21"
            }
            22 -> {
                return "ic_22"
            }
            23 -> {
                return "ic_23"
            }
            24 -> {
                return "ic_24"
            }
            25 -> {
                return "ic_25"
            }
            26 -> {
                return "ic_26"
            }
            29 -> {
                return "ic_29"
            }
            30 -> {
                return "ic_30"
            }
            31 -> {
                return "ic_31"
            }
            32 -> {
                return "ic_32"
            }
            33 -> {
                return "ic_33"
            }
            34 -> {
                return "ic_34"
            }
            35 -> {
                return "ic_35"
            }
            36 -> {
                return "ic_36"
            }
            37 -> {
                return "ic_37"
            }
            38 -> {
                return "ic_38"
            }
            39 -> {
                return "ic_39"
            }
            40 -> {
                return "ic_40"
            }
            41 -> {
                return "ic_41"
            }
            42 -> {
                return "ic_42"
            }
            43 -> {
                return "ic_43"
            }
            44 -> {
                return "ic_44"
            }
            else->{
                return "ic_baseline_wb_sunny_24"
            }
        }
    }
}
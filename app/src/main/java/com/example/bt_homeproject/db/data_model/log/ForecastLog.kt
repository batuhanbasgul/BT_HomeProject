package com.example.bt_homeproject.db.data_model.log

data class ForecastLog (
        val date:String,
        val iconIdDay:Int,
        val iconIdNight:Int,
        val iconPhraseDay:String,
        val iconPhraseNight:String,
        val maxTemperature:Int,
        val minTemperature:Int
        )
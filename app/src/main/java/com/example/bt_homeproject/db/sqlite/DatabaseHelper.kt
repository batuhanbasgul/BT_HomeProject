package com.example.bt_homeproject.db.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(
        context: Context
) : SQLiteOpenHelper(context, "log", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ForecastLog(date TEXT, iconIdDay INTEGER, iconIdNight INTEGER, iconPhraseDay TEXT, iconPhraseNight TEXT, maxTemperature INTEGER, minTemperature INTEGER)")
        db?.execSQL("CREATE TABLE PropertiesLog(title TEXT, subtitle TEXT, description TEXT, link TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ForecastLog")
        db?.execSQL("DROP TABLE IF EXISTS PropertiesLog")
        onCreate(db)
    }
}
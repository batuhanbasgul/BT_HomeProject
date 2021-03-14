package com.example.bt_homeproject.db.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.bt_homeproject.db.data_model.log.ForecastLog

object ForecastLogDao {
    fun addForecast(dbHelper: DatabaseHelper,forecastLog: ForecastLog){
        val db: SQLiteDatabase =dbHelper.writableDatabase
        val content: ContentValues = ContentValues()

        content.put("date",forecastLog.date)
        content.put("iconIdDay",forecastLog.iconIdDay)
        content.put("iconIdNight",forecastLog.iconIdNight)
        content.put("iconPhraseDay",forecastLog.iconPhraseDay)
        content.put("iconPhraseNight",forecastLog.iconPhraseNight)
        content.put("maxTemperature",forecastLog.maxTemperature)
        content.put("minTemperature",forecastLog.minTemperature)

        db.insertOrThrow("ForecastLog",null,content)
    }

    fun getForecast(dbHelper: DatabaseHelper): ArrayList<ForecastLog> {
        val arrayList:ArrayList<ForecastLog> = ArrayList()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ForecastLog",null)

        while (cursor.moveToNext()){
            val tempObject: ForecastLog = ForecastLog(cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getInt(cursor.getColumnIndex("iconIdDay")),
                    cursor.getInt(cursor.getColumnIndex("iconIdNight")),
                    cursor.getString(cursor.getColumnIndex("iconPhraseDay")),
                    cursor.getString(cursor.getColumnIndex("iconPhraseNight")),
                    cursor.getInt(cursor.getColumnIndex("maxTemperature")),
                    cursor.getInt(cursor.getColumnIndex("minTemperature")))
            arrayList.add(tempObject)
        }
        return arrayList
    }

    fun clearForecast(dbHelper: DatabaseHelper){
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL("DELETE FROM ForecastLog")
    }
}
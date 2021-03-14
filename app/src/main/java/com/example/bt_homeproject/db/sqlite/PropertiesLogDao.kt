package com.example.bt_homeproject.db.sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.bt_homeproject.db.data_model.log.PropertiesLog

object PropertiesLogDao {
    fun addProperties(dbHelper: DatabaseHelper,propertiesLog: PropertiesLog){
        val db:SQLiteDatabase=dbHelper.writableDatabase
        val content:ContentValues= ContentValues()

        content.put("title",propertiesLog.title)
        content.put("subTitle",propertiesLog.subTitle)
        content.put("description",propertiesLog.description)
        content.put("link",propertiesLog.link)

        db.insertOrThrow("PropertiesLog",null,content)
    }

    fun getProperties(dbHelper: DatabaseHelper):PropertiesLog{
        val arrayList:ArrayList<PropertiesLog> = ArrayList()
        val db:SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM PropertiesLog",null)

        while (cursor.moveToNext()){
            var tempObject:PropertiesLog = PropertiesLog(cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("subtitle")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("link")))
            arrayList.add(tempObject)
        }
        return arrayList[arrayList.size-1]
    }

    fun clearProperties(dbHelper: DatabaseHelper){
        val db:SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL("DELETE FROM PropertiesLog")
    }

}
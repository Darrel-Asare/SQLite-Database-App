package com.example.assignment4

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class SQLiteHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(database: SQLiteDatabase) {
        val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + Table_Column_ID + " INTEGER PRIMARY KEY, " + Table_Column_1_Name + " VARCHAR, " + Table_Column_2_Email + " VARCHAR, " + Table_Column_3_Password + " VARCHAR)"
        database.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        var DATABASE_NAME = "UsersDBInfo"
        const val TABLE_NAME = "UserTable"
        const val Table_Column_ID = "id"
        const val Table_Column_1_Name = "name"
        const val Table_Column_2_Email = "email"
        const val Table_Column_3_Password = "password"
    }
}
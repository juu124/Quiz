package com.example.quiz.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context): SQLiteOpenHelper(context, "tb_memo", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE tb_memo (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                content TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE tb_memo")
        onCreate(db)
    }
}
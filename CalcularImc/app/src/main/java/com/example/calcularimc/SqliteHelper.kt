package com.example.calcularimc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context): SQLiteOpenHelper(context, "imc.db", null, 1) {
    companion object{
        const val table_imc = "ImcRecords"
        const val column_id = "id"
        const val column_peso = "peso"
        const val column_altura = "altura"
        const val column_imc = "imc"
        const val column_categoria = "categoria"
        const val column_fecha = "fecha"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery ="""
            CREATE TABLE $table_imc(
                $column_id INTEGER PRIMARY KEY AUTOINCREMENT,
                $column_peso REAL,
                $column_altura REAL,
                $column_imc REAL,
                $column_categoria TEXT,
                $column_fecha TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $table_imc")
        onCreate(db)
    }

    fun insertIMC(peso: Double, altura: Double, imc: Double, categoria: String, fecha:String){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(column_peso, peso)
            put(column_altura, altura)
            put(column_imc, imc)
            put(column_categoria, categoria)
            put(column_fecha, fecha)
        }
        db.insert(table_imc, null, values)
        db.close()
    }

    //Cursor
    fun getAllRecords(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $table_imc", null)
    }
}
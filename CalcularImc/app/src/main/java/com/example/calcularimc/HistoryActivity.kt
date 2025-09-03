package com.example.calcularimc

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        //Elementos
        val btnbackhis = findViewById<ImageButton>(R.id.btnbackhis)
        val records = findViewById<ListView>(R.id.records)
        //----------------
        //SQLITE
        val dbHelper = SqliteHelper(this)
        val cursor = dbHelper.getAllRecords()
        val record = mutableListOf<String>()

        if(cursor.moveToFirst()){
            do{
                val peso = cursor.getString(cursor.getColumnIndexOrThrow("peso"))
                val altura = cursor.getString(cursor.getColumnIndexOrThrow("altura"))
                val imc = cursor.getString(cursor.getColumnIndexOrThrow("imc"))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))

                val registro = "Peso: $peso Kg, Altura: $altura m, IMC: $imc, Categoria: $categoria, Fecha: $fecha"

                record.add(registro)
            }while (cursor.moveToNext())
        }else{
            Toast.makeText(this, "No hay registros para mostrar", Toast.LENGTH_SHORT).show()
        }

        cursor.close()

        val recordsAdpater = ArrayAdapter(this, android.R.layout.simple_list_item_1, record)

        records.adapter = recordsAdpater
        //----------------------

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Click Boton btnbackhis para regresar al main
        btnbackhis.setOnClickListener {
            val intent6 = Intent(this, MainActivity::class.java)
            startActivity(intent6)
            finish()
        }
    }
}
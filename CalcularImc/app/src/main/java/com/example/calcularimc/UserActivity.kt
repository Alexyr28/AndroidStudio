package com.example.calcularimc

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        //Elementos
       val etnewName = findViewById<EditText>(R.id.etnewName)
       val btnsave = findViewById<Button>(R.id.btnsave)
       val btnback = findViewById<ImageButton>(R.id.btnback)
        //-----------------------
        //SharedPreferences
        val sp = getSharedPreferences("Datos", Context.MODE_PRIVATE)
        //-----------------------

        //Variables
        val guardarex = getString(R.string.guardarex)
        val errorguardar = getString(R.string.errorguardar)
        //-----------------------

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Click Boton guardar
        btnsave.setOnClickListener {
            try {
                val userName = etnewName.text.toString()
                if(userName.isNotEmpty()){
                    val editor = sp.edit()
                    editor.putString("Name", userName)
                    editor.apply()
                    Toast.makeText(this, "$guardarex", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Toast.makeText(this, "$errorguardar", Toast.LENGTH_SHORT).show()
            }
        }

        btnback.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
            finish()
        }
    }
}
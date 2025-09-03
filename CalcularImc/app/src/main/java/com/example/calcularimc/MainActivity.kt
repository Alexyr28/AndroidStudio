package com.example.calcularimc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.ThemeCalcularImc)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //Elementos
        val btnUser = findViewById<ImageButton>(R.id.btnUser)
        val tvName = findViewById<TextView>(R.id.tvName)
        val etPeso = findViewById<EditText>(R.id.etPeso)
        val etAltura = findViewById<EditText>(R.id.etAltura)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val btnCalcularimc = findViewById<Button>(R.id.btnCalcularimc)
        val btnhistory = findViewById<ImageButton>(R.id.btnhistory)
        //--------------------------------
        //SharedPreferences
        val sp = getSharedPreferences("Datos", Context.MODE_PRIVATE)
        //--------------------------------
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Recuperar Nombre
        val userName = sp.getString("Name", null)
        //---------------------------

        //Validar Si no hay nombre
        if (userName == null){
            val intent1 = Intent(this, UserActivity::class.java)
            startActivity(intent1)
            finish()
        }else{
            tvName.text = "Hola, $userName"
        }
        //-----------------------------------

        //SQLITE
        val dbhelper = SqliteHelper(this)
        //--------------------------------

        //Click Calcular IMC
        btnCalcularimc.setOnClickListener {
            try{
                val peso = etPeso.text.toString().trim()
                val altura = etAltura.text.toString().trim()

                if(peso.isNotEmpty() || altura.isNotEmpty()){
                    val peso = peso.toDouble()
                    val altura = altura.toDouble()
                    val imc = (peso/(altura*altura))

                    val cate = when {
                        imc < 18.5 -> "Bajo Peso"
                        imc in 18.5..24.9 -> "Peso Saludable"
                        imc in 25.0..29.9 -> "Sobrepeso"
                        else -> "Obesidad"
                    }

                    val imcdato = String.format("%.2f",(peso/(altura*altura)))
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val fecha = sdf.format(Date())

                    //IngresarDatos a SQLITE
                    dbhelper.insertIMC(peso,altura,imc,cate,fecha)

                    tvResultado.text = "Tu IMC es: $imcdato \n Tu categoria es: $cate"

                }else{
                    Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                }

            }catch (e: Exception){
                Toast.makeText(this, "Error al calcular el IMC, Verificar Datos", Toast.LENGTH_SHORT).show()
            }
        }
        //---------------------------------------
        //Click Ir a Editar Campo
        btnUser.setOnClickListener {
            val intent3 = Intent(this, UserActivity::class.java)
            startActivity(intent3)
            finish()
        }
        //--------------------------
        //Click Ir a Historia
        btnhistory.setOnClickListener {
            val inten4 = Intent(this, HistoryActivity::class.java)
            startActivity(inten4)
            finish()
        }
    }
}
package com.example.calcularimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Elementos
        val etusernew = findViewById<EditText>(R.id.etUserNew)
        val etpassword = findViewById<EditText>(R.id.etPassword)
        val btnlog = findViewById<Button>(R.id.btnLogin)
        val btnreg = findViewById<Button>(R.id.btnreg)


        //Firebase
        val auth = FirebaseAuth.getInstance()

        //Strings
        val usernoexist = getString(R.string.usernoexist)
        val credinvalid = getString(R.string.credinvalid)
        val error2 = getString(R.string.error2)
        val campovacio = getString(R.string.campovacio)
        val regexitoso = getString(R.string.regexitoso)
        val userexist = getString(R.string.userexist)
        //---------------------------

        btnlog.setOnClickListener {
            try {
                val usuario = etusernew.text.toString()
                val password = etpassword.text.toString()
                if (usuario.isNotEmpty() && password.isNotEmpty()){
                    auth.signInWithEmailAndPassword(usuario, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val paghome = Intent(this, MainActivity::class.java)
                            startActivity(paghome)
                            finish()
                        }else{
                            if(it.exception is FirebaseAuthInvalidUserException){
                                Toast.makeText(this,"$usernoexist", Toast.LENGTH_SHORT).show()
                            }else if (it.exception is FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(this,"$credinvalid", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this,"$error2", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    Toast.makeText(this,"$campovacio", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Toast.makeText(this,"$error2", Toast.LENGTH_SHORT).show()
            }
        }

        btnreg.setOnClickListener {
            try {
                val usuario1 = etusernew.text.toString()
                val password1 = etpassword.text.toString()
                if(usuario1.isNotEmpty() && password1.isNotEmpty()){
                    auth.createUserWithEmailAndPassword(usuario1, password1).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "$regexitoso", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"$userexist", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch (e: Exception){
                Toast.makeText(this,"$error2", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
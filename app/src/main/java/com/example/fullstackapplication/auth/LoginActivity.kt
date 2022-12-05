package com.example.fullstackapplication.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fullstackapplication.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLoingLogin=findViewById<Button>(R.id.btnLoginLogin)
        val etLoginEmail=findViewById<EditText>(R.id.etLoginEmail)
        val etLoginPw=findViewById<EditText>(R.id.etLoginPw)

        btnLoingLogin.setOnClickListener {
            val email=etLoginEmail.text.toString()
                val pw= etLoginPw.text.toString()
            Toast.makeText(this@LoginActivity,"$email, $pw",Toast.LENGTH_SHORT).show()
        }
    }
}
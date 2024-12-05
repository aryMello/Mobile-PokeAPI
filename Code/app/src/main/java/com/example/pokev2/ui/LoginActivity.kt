package com.example.pokev2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokev2.PokemonActivity
import com.example.pokev2.R
import com.example.pokev2.adapter.PokemonAdapter
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // inicializa firebase
        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val createAccountText: TextView = findViewById(R.id.createAccountText)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor insira seu email e senha", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        createAccountText.setOnClickListener {
            // Navega para criar conta
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // logado com sucesso
                    Toast.makeText(this, "Usuario logado com sucesso!", Toast.LENGTH_SHORT).show()
                    // Navega para PokemonActivity
                    val intent = Intent(this, PokemonActivity::class.java) // Redireciona para PokemonActivity
                    startActivity(intent)
                    finish() // fecha LoginActivity
                } else {
                    // Show error message
                    Toast.makeText(this, "Falha ao logar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}

package com.example.pokev2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)

        createAccountButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success
                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    // You can redirect to another activity here
                } else {
                    // If sign up fails, display a message to the user
                    Toast.makeText(this, "Falha ao criar conta: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}


package com.example.pokev2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pokev2.R
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Referências para os campos de entrada e botão
        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)

        // Listener para o botão de mudar senha
        changePasswordButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Valida se os campos estão preenchidos
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se as senhas coincidem
            if (newPassword != confirmPassword) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Realiza a mudança da senha
            changePassword(newPassword)
        }

        val backToAccountText: TextView = findViewById(R.id.backToAccountText)

        backToAccountText.setOnClickListener {
            // Navega para voltar a conta
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    // Função para alterar a senha usando FirebaseAuth
    private fun changePassword(newPassword: String) {
        val user = auth.currentUser

        user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Senha atualizada com sucesso", Toast.LENGTH_SHORT).show()
                finish() // Fecha a activity após a atualização bem-sucedida
            } else {
                Toast.makeText(this, "Erro ao atualizar senha: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

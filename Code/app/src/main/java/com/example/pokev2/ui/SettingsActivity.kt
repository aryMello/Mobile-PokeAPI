package com.example.pokev2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pokev2.PokemonActivity
import com.example.pokev2.R
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser


        // Back button to navigate to PokemonActivity
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            val intent = Intent(this, PokemonActivity::class.java)
            startActivity(intent)
            finish() // Finish SettingsActivity to prevent going back to it
        }

        // Opcao de mudar senha
        findViewById<TextView>(R.id.changePasswordText).setOnClickListener {
            handlePasswordChange()
        }

        findViewById<ImageButton>(R.id.editPasswordButton).setOnClickListener {
            handlePasswordChange()
        }

        // Opcao de logout
        findViewById<TextView>(R.id.logoutText).setOnClickListener {
            handleLogout()
        }

        findViewById<ImageButton>(R.id.logoutButton).setOnClickListener {
            handleLogout()
        }
    }

    private fun handleLogout() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handlePasswordChange() {
        val intent = Intent(this, ChangePasswordActivity::class.java)
        startActivity(intent)
    }

}

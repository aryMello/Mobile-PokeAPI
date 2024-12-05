package com.example.pokev2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokev2.R
import com.example.pokev2.utils.CapturedPokemonManager


class AboutPokemonMinhaPokedexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_pokemon_minha_pokedex)
        Log.d("AboutPokemon", "Activity started")
            ?: "Pokemon sem id"
        val pokemonName = intent.getStringExtra("pokemonName") ?: "Pokémon Desconhecido"
        val pokemonImage = intent.getStringExtra("pokemonImage") ?: "Pokemon sem imagem"
        val pokemonHeight = intent.getStringExtra("pokemonHeight") ?: "Pokemon sem altura"
        val pokemonWeight = intent.getStringExtra("pokemonWeight") ?: "Pokemon sem peso"

        val pokemonNameTextView: TextView = findViewById(R.id.pokemonNameTextView)
        val pokemonImageView: ImageView = findViewById(R.id.pokemonImageView)
        val pokemonHeightTextView: TextView = findViewById(R.id.pokemonHeightTextView)
        val pokemonWeightTextView: TextView = findViewById(R.id.pokemonWeightTextView)

        pokemonNameTextView.text = pokemonName
        pokemonHeightTextView.text = pokemonHeight
        pokemonWeightTextView.text = pokemonWeight

        Glide.with(this)
            .load(pokemonImage)
            .placeholder(R.drawable.placeholder_image)
            .into(pokemonImageView)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val releaseButton: Button = findViewById(R.id.libertarButton)
        releaseButton.setOnClickListener{
            val pokemonId = intent.getIntExtra("pokemonId", -1)
            if (pokemonId != -1) {
                CapturedPokemonManager.libertaPokemon(
                    pokemonId = pokemonId,
                    onSuccess = {
                        Toast.makeText(this, "Pokémon libertado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = { exception ->
                        Toast.makeText(this, "Erro ao liberar Pokémon: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }

        }

        val atualizarButton: Button = findViewById(R.id.atualizarButton)
        val editNameEditText: EditText = findViewById(R.id.editNameEditText)

        atualizarButton.setOnClickListener {
            val newName = editNameEditText.text.toString()
            val pokemonId = intent.getIntExtra("pokemonId", -1)
            if (pokemonId != -1 && newName.isNotEmpty()) {
                CapturedPokemonManager.updatePokemonName(
                    pokemonId = pokemonId,
                    newName = newName,
                    onSuccess = {
                        Toast.makeText(this, "Nome do Pokémon editado com sucesso!", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { exception ->
                        Toast.makeText(this, "Erro ao editar nome do Pokémon: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            } else if (newName.isEmpty()) {
                Toast.makeText(this, "Por favor, insira um nome válido para o Pokémon.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}


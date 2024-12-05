package com.example.pokev2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.pokev2.model.Pokemon
import com.example.pokev2.utils.CapturedPokemonManager
import com.example.pokev2.utils.RetrofitClient
import com.example.pokev2.utils.TypeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutPokemonActivity : AppCompatActivity() {

    private lateinit var backgroundView: View
    private lateinit var pokemonImageView: ImageView
    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonIdTextView: TextView
    private lateinit var pokemonHeightTextView: TextView
    private lateinit var pokemonWeightTextView: TextView
    private lateinit var pokemonbase_experienceTextView: TextView
    private lateinit var weaknessContainer: LinearLayout
    private lateinit var capturarButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_pokemon)

        // Initialize views
        backgroundView = findViewById(R.id.backgroundDesign)
        pokemonImageView = findViewById(R.id.pokemonImageView)
        pokemonNameTextView = findViewById(R.id.pokemonNameTextView)
        pokemonIdTextView = findViewById(R.id.pokemonIdTextView)
        pokemonHeightTextView = findViewById(R.id.textViewHeight)
        pokemonWeightTextView = findViewById(R.id.textViewWeight)
        pokemonbase_experienceTextView = findViewById(R.id.textViewbase_experience)
        weaknessContainer = findViewById(R.id.weaknessContainer)
        capturarButton = findViewById(R.id.buttonCapturar)

        // Retrieve data from Intent
        val pokemonName = intent.getStringExtra("pokemonName") ?: "Unknown"
        val pokemonId = intent.getIntExtra("pokemonId", -1)
        val pokemonImage = intent.getStringExtra("pokemonImage") ?: ""
        val pokemonTypes = intent.getStringArrayExtra("pokemonTypes")?.toList() ?: listOf()
        val pokemonHeight = intent.getStringExtra("pokemonHeight") ?: "Unknown"
        val pokemonWeight = intent.getStringExtra("pokemonWeight") ?: "Unknown"
        val pokemonbase_experience = intent.getStringExtra("pokemonbase_experience") ?: "Unknown"

        // Apply background gradient
        val primaryType = pokemonTypes.firstOrNull() ?: "normal"
       // TypeUtils.getGradientForType(primaryType, backgroundView, this)

        // Populate views with data
        pokemonNameTextView.text = pokemonName
        pokemonIdTextView.text = String.format("#%03d", pokemonId)
        pokemonHeightTextView.text = pokemonHeight
        pokemonWeightTextView.text = pokemonWeight
        pokemonbase_experienceTextView.text = pokemonbase_experience

        // Load Pokémon image
        Glide.with(this)
            .load(pokemonImage)
            .placeholder(R.drawable.placeholder_image)
            .into(pokemonImageView)

        // Back Button Functionality
        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Return to the previous activity
        }


        capturarButton.setOnClickListener {
            val pokemon = Pokemon(
                game_index = pokemonIdTextView.text.toString().filter { it.isDigit() }.toInt(),
                name = pokemonNameTextView.text.toString(),
                imageUrl = pokemonImage,
                types = pokemonTypes,
                height = pokemonHeightTextView.text.toString(),
                weight = pokemonWeightTextView.text.toString()
            )
            CapturedPokemonManager.addPokemon(
                pokemon,
                onSuccess = {
                    Toast.makeText(this, "${pokemon.name} capturado com sucesso!", Toast.LENGTH_SHORT).show()
                },
                onFailure = { exception ->
                    Toast.makeText(this, "Erro ao capturar ${pokemon.name}: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            )
            Toast.makeText(this, "${pokemon.name} capturado!", Toast.LENGTH_SHORT).show()
        }
    }
}

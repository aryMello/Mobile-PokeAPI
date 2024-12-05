package com.example.pokev2.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.R
import com.example.pokev2.adapter.PokemonAdapter
import com.example.pokev2.model.Pokemon
import com.example.pokev2.utils.CapturedPokemonManager

class MinhaPokedexActivity : AppCompatActivity() {

    private lateinit var pokedexAdapter: PokemonAdapter
    private var capturedPokemon: List<Pokemon> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_pokedex)

        val backButton: Button = findViewById(R.id.backButton)
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val pokemonRecyclerView: RecyclerView = findViewById(R.id.pokemonRecyclerView)

        // Botão de voltar para a tela anterior
        backButton.setOnClickListener {
            finish()
        }

        // Configuração inicial do RecyclerView
        pokedexAdapter = PokemonAdapter(emptyList()) { pokemon ->
            navigateToPokemonDetails(pokemon)
        }
        pokemonRecyclerView.layoutManager = GridLayoutManager(this, 3)
        pokemonRecyclerView.adapter = pokedexAdapter

        // Carregar a lista de Pokémon capturados
        loadCapturedPokemon()

        // Configurar o filtro de busca
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPokemonList(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadCapturedPokemon() {
        CapturedPokemonManager.getCapturedPokemon(
            onSuccess = { pokemonList ->
                runOnUiThread {
                    capturedPokemon = pokemonList
                    pokedexAdapter.updateList(capturedPokemon)
                    Toast.makeText(this, "Pokémons carregados!", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { exception ->
                runOnUiThread {
                    Toast.makeText(this, "Erro ao carregar Pokémons: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun filterPokemonList(query: String) {
        val filteredList = capturedPokemon.filter { it.name.contains(query, ignoreCase = true) }
        pokedexAdapter.updateList(filteredList)
    }

    private fun navigateToPokemonDetails(pokemon: Pokemon) {
        val intent = Intent(this, AboutPokemonMinhaPokedexActivity::class.java).apply {
            putExtra("pokemonName", pokemon.name)
            putExtra("pokemonImage", pokemon.imageUrl)
            putExtra("pokemonTypes", pokemon.types?.toTypedArray())
            putExtra("pokemonHeight", pokemon.height)
            putExtra("pokemonWeight", pokemon.weight)
            putExtra("pokemonbase_experience", pokemon.base_experience.toString())
            putExtra("pokemonId", pokemon.game_index)
        }
        startActivity(intent)
    }
}

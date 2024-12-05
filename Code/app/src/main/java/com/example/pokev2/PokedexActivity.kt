package com.example.pokev2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.adapter.PokemonAdapter
import com.example.pokev2.model.Pokemon
import com.example.pokev2.model.PokemonListResponse
import com.example.pokev2.ui.MinhaPokedexActivity
import com.example.pokev2.ui.SettingsActivity
import com.example.pokev2.utils.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.pokemonRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val addButton = findViewById<ImageButton>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, MinhaPokedexActivity::class.java)
            startActivity(intent)
        }

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPokemon(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        fetchPokemonData()
    }

    private fun fetchPokemonData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemonListResponse: PokemonListResponse = RetrofitClient.pokeApiService.getPokemons()
                val pokemonSummaries = pokemonListResponse.results

                for (pokemonSummary in pokemonSummaries) {
                    val game_index = pokemonSummary.url.split("/".toRegex()).dropLast(1).last().toInt()
                    val pokemonResponse = RetrofitClient.pokeApiService.getPokemon(game_index)


                    val pokemon = Pokemon(
                        game_index = game_index,
                        name = pokemonResponse.name.capitalize(),
                        imageUrl = pokemonResponse.sprites.front_default ?: "",
                        types = pokemonResponse.types.map { it.type.name },
                        height = "${pokemonResponse.height / 10.0}m",
                        weight = "${pokemonResponse.weight / 10.0}kg"
                    )
                    pokemonList.add(pokemon)
                }

                withContext(Dispatchers.Main) {
                    adapter = PokemonAdapter(pokemonList.toMutableList())
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("PokemonActivity", "Error fetching data", e)
            }
        }
    }

    private fun filterPokemon(query: String?) {
        val lowercaseQuery = query?.lowercase() ?: ""
        val filteredList = pokemonList.filter { pokemon ->
            pokemon.name.lowercase().contains(lowercaseQuery) ||
                    pokemon.types.any { type -> type.lowercase().contains(lowercaseQuery) }
        }
        adapter.updateList(filteredList)
    }
}

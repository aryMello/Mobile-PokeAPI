package com.example.pokev2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.adapter.PokemonAdapter
import com.example.pokev2.model.Pokemon
import com.example.pokev2.model.PokemonListResponse
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
        setContentView(R.layout.activity_pokemon)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPokemonData()
    }

    private fun fetchPokemonData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemonListResponse: PokemonListResponse = RetrofitClient.pokeApiService.getPokemons()
                val pokemonSummaries = pokemonListResponse.results

                for (pokemonSummary in pokemonSummaries) {
                    val id = pokemonSummary.url.split("/".toRegex()).dropLast(1).last().toInt()
                    val pokemonResponse = RetrofitClient.pokeApiService.getPokemon(id)

                    // Extrai tipo de PokemonResponse
                    val types = pokemonResponse.types.map { it.type.name } // pega nome dos tipos
                    val pokemon = Pokemon(pokemonResponse.name, pokemonResponse.sprites.front_default, types)
                    pokemonList.add(pokemon)
                }

                // manda UI para main thread
                withContext(Dispatchers.Main) {
                    adapter = PokemonAdapter(pokemonList)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("PokemonActivity", "Error ao fetch data", e)
            }
        }
    }


}

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.R
import com.example.pokev2.adapter.PokemonAdapter
import com.example.pokev2.api.PokeApiService
import com.example.pokev2.model.Pokemon
import com.example.pokev2.model.PokemonListResponse
import com.example.pokev2.utils.RetrofitClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private lateinit var pokeApiService: PokeApiService
    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupRetrofit()
        fetchPokemonData() // Fetch Pokémon data
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 colunas por grid
    }

    private fun setupRetrofit() {
        val baseUrl = "https://pokeapi.co/api/v2/"
        val gson: Gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        pokeApiService = retrofit.create(PokeApiService::class.java)
    }

    private fun fetchPokemonData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetch lista de pokemon
                val pokemonListResponse: PokemonListResponse = RetrofitClient.pokeApiService.getPokemons()
                val pokemonSummaries = pokemonListResponse.results

                // loop sumario e fetch detalhes
                for (pokemonSummary in pokemonSummaries) {
                    val id = pokemonSummary.url.split("/".toRegex()).dropLast(1).last().toInt()
                    val pokemonResponse = RetrofitClient.pokeApiService.getPokemon(id)

                    // Extrai os detalhes de PokemonResponse
                    val types = pokemonResponse.types.map { it.type.name } // pega o nome correto no json
                    val pokemon = Pokemon(pokemonResponse.name, pokemonResponse.sprites.front_default, types)
                    pokemonList.add(pokemon)
                }

                // bota UI na main thread
                withContext(Dispatchers.Main) {
                    adapter = PokemonAdapter(pokemonList)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("PokemonActivity", "Error ao buscar data", e)
            }
        }
    }

}

package com.example.pokev2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.R
import com.example.pokev2.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter(private val pokemonList: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.pokemonNameTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.pokemonImageView)
        private val typeTextView: TextView = itemView.findViewById(R.id.pokemonTypeTextView)

        fun bind(pokemon: Pokemon) {
            nameTextView.text = pokemon.name.capitalize() // Capitalize for better visibility
            typeTextView.text = pokemon.types.joinToString(", ") // Show types as a string
            Picasso.get().load(pokemon.imageUrl).into(imageView) // Load image with Picasso

            // Set background color based on the first type
            val type = pokemon.types.firstOrNull()?.toLowerCase() ?: "normal" // Default type
            typeTextView.setBackgroundColor(getColorForType(itemView.context, type)) // Set background color
        }

        // Function to get the color for each type
        private fun getColorForType(context: Context, type: String): Int {
            return when (type) {
                "fire" -> ContextCompat.getColor(context, R.color.fire)
                "water" -> ContextCompat.getColor(context, R.color.water)
                "grass" -> ContextCompat.getColor(context, R.color.grass)
                "electric" -> ContextCompat.getColor(context, R.color.electric)
                "normal" -> ContextCompat.getColor(context, R.color.normal)
                "poison" -> ContextCompat.getColor(context, R.color.poison)
                "psychic" -> ContextCompat.getColor(context, R.color.psychic)
                "ghost" -> ContextCompat.getColor(context, R.color.ghost)
                "fighting" -> ContextCompat.getColor(context, R.color.fighting)
                // Add more types as needed
                else -> ContextCompat.getColor(context, R.color.normal) // Default color
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size
}

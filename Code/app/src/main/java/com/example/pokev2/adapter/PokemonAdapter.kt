package com.example.pokev2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.AboutPokemonActivity
import com.example.pokev2.R
import com.example.pokev2.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private var pokemonList: List<Pokemon>,
    private val onItemClick: ((Pokemon) -> Unit)? = null
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.pokemonNameTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.pokemonImageView)
        private val typeTextView: TextView = itemView.findViewById(R.id.pokemonTypeTextView)

        fun bind(pokemon: Pokemon) {
            nameTextView.text = pokemon.name.capitalize()
            typeTextView.text = pokemon.types.joinToString(", ")
            Picasso.get().load(pokemon.imageUrl).into(imageView)

            val type = pokemon.types.firstOrNull()?.toLowerCase() ?: "normal"
            typeTextView.setTextColor(getColorForType(itemView.context, type))

            itemView.setOnClickListener {
                if (onItemClick != null) {
                    onItemClick?.let { it1 -> it1(pokemon) }
                } else {
                    val context = itemView.context
                    val intent = Intent(context, AboutPokemonActivity::class.java).apply {
                        putExtra("pokemonName", pokemon.name)
                        putExtra("pokemonImage", pokemon.imageUrl)
                        putExtra("pokemonTypes", pokemon.types.toTypedArray())
                        putExtra("pokemonHeight", pokemon.height)
                        putExtra("pokemonWeight", pokemon.weight)
                        putExtra("pokemonbase_experience", pokemon.base_experience.toString())
                        putExtra("pokemonId", pokemon.game_index)
                    }
                    context.startActivity(intent)
                }
            }
        }

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
                "fairy" -> ContextCompat.getColor(context, R.color.fairy)
                "ground" -> ContextCompat.getColor(context, R.color.ground)
                "bug" -> ContextCompat.getColor(context, R.color.bug)
                "rock" -> ContextCompat.getColor(context, R.color.rock)
                else -> ContextCompat.getColor(context, R.color.normal)
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

    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }
}

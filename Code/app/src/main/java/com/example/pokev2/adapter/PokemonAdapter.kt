package com.example.pokev2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokev2.R
import com.example.pokev2.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter(private val pokemonList: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.pokemonNameTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.pokemonImageView)
        private val typeTextView: TextView = itemView.findViewById(R.id.pokemonTypeTextView) // TextView para os tipos

        fun bind(pokemon: Pokemon) {
            nameTextView.text = pokemon.name.capitalize() // capitaliza para melhor visualizacao
            typeTextView.text = pokemon.types.joinToString(", ") // Mostra tipo em string
            Picasso.get().load(pokemon.imageUrl).into(imageView) // Carrega imagem com picasso
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

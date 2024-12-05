package com.example.pokev2.utils

import com.example.pokev2.model.Pokemon
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object CapturedPokemonManager {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("capturedPokemon")

    fun addPokemon(pokemon: Pokemon, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val pokemonId = pokemon.game_index.toString()
        database.child(pokemonId)
            .setValue(pokemon)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getCapturedPokemon(onSuccess: (List<Pokemon>) -> Unit, onFailure: (Exception) -> Unit) {
        database.get()
            .addOnSuccessListener { snapshot ->
                val pokemonList = snapshot.children.mapNotNull { it.getValue(Pokemon::class.java) }
                onSuccess(pokemonList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    fun updatePokemonName(pokemonId: Int, newName: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val pokemonIdStr = pokemonId.toString()
        database.child(pokemonIdStr).child("name") .setValue(newName)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                exception -> onFailure(exception)
            }
    }


    fun libertaPokemon(pokemonId: Int, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val pokemonIdStr = pokemonId.toString()
        database.child(pokemonIdStr)
            .removeValue()
            .addOnSuccessListener {
                onSuccess()
                }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }

    }
}
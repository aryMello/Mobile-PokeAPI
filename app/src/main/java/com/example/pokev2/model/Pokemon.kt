package com.example.pokev2.model

data class Pokemon(
    val name: String,
    val imageUrl: String, // imagem URL
    val types: List<String> // Lista dos tipos de Pokémon
)

package com.example.pokev2.model

data class PokemonResponse(
    val game_index: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val height: String,
    val weight: String,
    val base_experience: Int,
)

data class Sprites(
    val front_default: String // URL para a imagem capa do pokemon
)

data class PokemonType(
    val type: TypeDetail // pokemon tem detalhe do tipo
)

data class TypeDetail(
    val name: String // Tipo  ("fire", "water")
)

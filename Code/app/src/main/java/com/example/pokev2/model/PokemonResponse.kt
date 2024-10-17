package com.example.pokev2.model

data class PokemonResponse(
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val imageURL: String
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

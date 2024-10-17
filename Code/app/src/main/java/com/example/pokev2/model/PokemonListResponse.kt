package com.example.pokev2.model

data class PokemonListResponse(
    val results: List<PokemonSummary>
)

data class PokemonSummary(
    val name: String,
    val url: String
)

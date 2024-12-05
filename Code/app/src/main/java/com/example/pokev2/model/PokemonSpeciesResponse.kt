package com.example.pokev2.model

data class PokemonSpeciesResponse(
    val flavor_text_entries: List<FlavorTextEntry>,
    val evolution_chain: EvolutionChainUrl

)

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language
)

data class Language(
    val name: String
)

data class EvolutionChainUrl(
    val url: String
)

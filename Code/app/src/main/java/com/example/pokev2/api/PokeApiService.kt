package com.example.pokev2.api

import com.example.pokev2.model.PokemonListResponse
import com.example.pokev2.model.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse


    @GET("pokemon?limit=100") // maximo de pokemon a mostrar
    suspend fun getPokemons(): PokemonListResponse
}


data class PokemonResponse(
    val game_index: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<Type>,
    val height: Double,
    val weight: Double,
    val base_experience: Int
)

data class Sprites(
    val front_default: String
)

data class Type(
    val type: TypeDetail
)

data class TypeDetail(
    val name: String
)

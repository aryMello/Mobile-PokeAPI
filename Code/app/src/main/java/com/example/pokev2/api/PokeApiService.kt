package com.example.pokev2.api

import com.example.pokev2.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse

    @GET("pokemon?limit=100") // maximo de pokemon a mostrar
    suspend fun getPokemons(): PokemonListResponse
}


data class PokemonResponse(
    val name: String,
    val sprites: Sprites,
    val types: List<Type>
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

package com.example.pokev2.utils

import com.example.pokev2.api.PokeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/" // URL base da PokéAPI

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pokeApiService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}

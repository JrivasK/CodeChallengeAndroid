package com.example.codechallengeandroid.home.api

import com.example.codechallengeandroid.home.data.response.EvolutionChain
import com.example.codechallengeandroid.home.data.response.ListPokemon
import com.example.codechallengeandroid.home.data.response.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonAsync(@Query("limit") limit: Int = 151): Response<ListPokemon>

    @GET("pokemon-species/{name}/")
    suspend fun getPokemonInfoByNameAsync(@Path("name") name: String): Response<Pokemon>

    @GET("pokemon/{name}/")
    suspend fun getPokemonAbilitiesAsync(@Path("name") name: String): Response<Pokemon>

    @GET
    suspend fun getEvolutionsAsync(@Url url: String): Response<EvolutionChain>
}
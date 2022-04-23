package com.example.codechallengeandroid.repository

import com.example.codechallengeandroid.home.api.ApiService
import javax.inject.Inject

class RepositoryPokemonRetrofit @Inject constructor (private val apiService: ApiService) {
    suspend fun getPokemonAsync() = apiService.getPokemonAsync()
    suspend fun getEvolutionAsync(url:String) = apiService.getEvolutionsAsync(url)
    suspend fun getPokemonAbilitiesAsync(name: String) = apiService.getPokemonAbilitiesAsync(name)
    suspend fun getPokemonInfoByNameAsync(name: String) = apiService.getPokemonInfoByNameAsync(name)
}
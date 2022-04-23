package com.example.codechallengeandroid.repository

import com.example.codechallengeandroid.data.Pokemon
import com.example.codechallengeandroid.repository.room.pokemon.PokemonDao
import javax.inject.Inject

class RepositoryPokemonRoom @Inject constructor(private val pokemonDao: PokemonDao) {
    suspend fun getPokemon() = pokemonDao.getPokemon()
    suspend fun insertPokemon(pokemon: Pokemon) = pokemonDao.insertPokemon(pokemon)
    suspend fun updatePokemon(pokemon: Pokemon) = pokemonDao.updatePokemon(pokemon)
    suspend fun findPokemonByName(name: String) = pokemonDao.findPokemonById(name)
}
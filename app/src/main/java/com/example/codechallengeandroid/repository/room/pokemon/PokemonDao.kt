package com.example.codechallengeandroid.repository.room.pokemon

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.codechallengeandroid.data.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    suspend fun getPokemon() : List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(product: Pokemon): Long

    @Update
    suspend fun updatePokemon(product: Pokemon)

    @Query("SELECT * FROM pokemon where name = :name")
    suspend fun findPokemonById(name: String): Pokemon
}
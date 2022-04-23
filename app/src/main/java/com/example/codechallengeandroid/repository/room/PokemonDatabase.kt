package com.example.codechallengeandroid.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codechallengeandroid.data.Pokemon
import com.example.codechallengeandroid.repository.room.pokemon.PokemonDao

@Database(
    entities = [Pokemon::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}
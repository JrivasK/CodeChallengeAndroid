package com.example.codechallengeandroid.di

import android.content.Context
import androidx.room.Room
import com.example.codechallengeandroid.repository.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun providesDataBase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, PokemonDatabase::class.java, "pokemon_data_base")
            .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase) = db.getPokemonDao()
}
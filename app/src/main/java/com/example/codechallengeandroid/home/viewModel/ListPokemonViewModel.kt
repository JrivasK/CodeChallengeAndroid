package com.example.codechallengeandroid.home.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallengeandroid.core.CoreViewModel
import com.example.codechallengeandroid.data.Pokemon
import com.example.codechallengeandroid.repository.RepositoryPokemonRetrofit
import com.example.codechallengeandroid.repository.RepositoryPokemonRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    private val repositoryRetrofit: RepositoryPokemonRetrofit,
    private val repositoryPokemonRoom: RepositoryPokemonRoom
) : CoreViewModel() {

    private val pokemonLiveData = MutableLiveData<List<Pokemon>>()
    fun getPokemon() = pokemonLiveData
    private val updatePokemon = MutableLiveData<Int>()
    fun isUpdated() = updatePokemon

    init {
        loadPokemon()
    }

    fun reloadPokemon() {
        isLoading().postValue(true)
        viewModelScope.launch {
            val pokemonRoom = repositoryPokemonRoom.getPokemon()
            if (pokemonRoom.isNotEmpty()) {
                pokemonLiveData.postValue(pokemonRoom)
                isLoading().postValue(false)
            }
        }
    }
    private fun loadPokemon() {
        isLoading().postValue(true)
        viewModelScope.launch {
            val pokemonRoom = repositoryPokemonRoom.getPokemon()
            if(pokemonRoom.isNotEmpty()){
                isLoading().postValue(false)
                pokemonLiveData.postValue(pokemonRoom)
            } else{
                val pokemon = repositoryRetrofit.getPokemonAsync()
                when (pokemon.isSuccessful) {
                    true -> {
                        with(pokemon.body()!!) {
                            var pokemonList = listOf<Pokemon>()
                            var count = 1;
                            this.results.forEach { (name, url) ->
                                pokemonList = pokemonList + Pokemon(name!!, url!!, count)
                                savePokemonRoom(Pokemon(name, url, count))
                                count++
                            }
                            isLoading().postValue(false)
                            pokemonLiveData.postValue(pokemonList)
                        }
                    }
                    else -> {
                        isLoading().postValue(false)
                        getError().postValue("Error al cargar datos")
                    }
                }
            }
        }
    }

    private fun savePokemonRoom(pokemon: Pokemon) {
        viewModelScope.launch {
            repositoryPokemonRoom.insertPokemon(pokemon)
        }
    }

    fun changePokemonFavoriteOrError(name: String, position: Int) {
        isLoading().postValue(true)
        viewModelScope.launch {
            val pokemonRoom = repositoryPokemonRoom.findPokemonByName(name)
            if (pokemonRoom!=null) {
                    pokemonRoom.favorite = false
                    pokemonRoom.error = false
                repositoryPokemonRoom.updatePokemon(pokemonRoom)
                isLoading().postValue(false)
                isUpdated().postValue(position)
            }
        }
    }
}
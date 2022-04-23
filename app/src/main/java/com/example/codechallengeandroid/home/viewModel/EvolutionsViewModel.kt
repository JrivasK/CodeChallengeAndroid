package com.example.codechallengeandroid.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codechallengeandroid.core.CoreViewModel
import com.example.codechallengeandroid.home.data.response.BaseModel
import com.example.codechallengeandroid.home.data.response.Chain
import com.example.codechallengeandroid.home.data.response.EvolutionChain
import com.example.codechallengeandroid.repository.RepositoryPokemonRetrofit
import com.example.codechallengeandroid.repository.RepositoryPokemonRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.lang.Exception
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EvolutionsViewModel @Inject constructor(
    private val repositoryRetrofit: RepositoryPokemonRetrofit,
    private val repositoryPokemonRoom: RepositoryPokemonRoom
) : CoreViewModel() {

    private val evolutionsLiveData = MutableLiveData<List<BaseModel>>()
    fun getEvolutions() = evolutionsLiveData
    private var list: List<BaseModel> = listOf()

    private val updatePokemon = MutableLiveData<Boolean>()
    fun isUpdated() = updatePokemon

    fun loadPokemon(url: String) {
        isLoading().postValue(true)
        viewModelScope.launch {
            try {
                val chain = repositoryRetrofit.getEvolutionAsync(url)
                when (chain.isSuccessful) {
                    true -> {
                        with(chain.body()!!) {
                            isLoading().postValue(false)
                            evolutionsLiveData.postValue(createSpecieList(chain.body()!!))
                        }
                    }
                    else -> {
                        isLoading().postValue(false)
                        getError().postValue("Error al cargar datos")
                    }
                }
            } catch (e: Exception) {
                getError().postValue(e.localizedMessage)
                isLoading().postValue(true)
            }
        }
    }

    private fun createSpecieList(body: EvolutionChain): List<BaseModel>? {
        list = listOf()
        if (body.chain.species != null) {
            list = list + body.chain.species
        }
        validateEvolvesTo(body.chain.evolvesTo)
        return list
    }

    private fun validateEvolvesTo(evolvesTo: List<Chain>) {
        for (specie in evolvesTo) {
            if (specie.species != null) {
                list = list + specie.species
            }
            if (specie.evolvesTo.isNotEmpty()) {
                validateEvolvesTo(specie.evolvesTo)
            }
        }
    }

    fun loadPokemonByName(name: String) {
        isLoading().postValue(true)
        viewModelScope.launch {
            val pokemonRoom = repositoryPokemonRoom.findPokemonByName(name)
            if (pokemonRoom != null) {
                val favorite = Random.nextInt(0, 100) % 2 == 0
                if (favorite) {
                    pokemonRoom.favorite = true
                } else {
                    pokemonRoom.error = true
                }
                repositoryPokemonRoom.updatePokemon(pokemonRoom)
                isLoading().postValue(false)
                isUpdated().postValue(true)
            }
        }
    }
}
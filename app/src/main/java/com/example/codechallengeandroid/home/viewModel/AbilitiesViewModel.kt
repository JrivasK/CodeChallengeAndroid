package com.example.codechallengeandroid.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallengeandroid.core.CoreViewModel
import com.example.codechallengeandroid.home.data.response.Chain
import com.example.codechallengeandroid.home.data.response.Pokemon
import com.example.codechallengeandroid.repository.RepositoryPokemonRetrofit
import com.example.codechallengeandroid.repository.RepositoryPokemonRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AbilitiesViewModel @Inject constructor(
    private val repositoryRetrofit: RepositoryPokemonRetrofit,
    private val repositoryPokemonRoom: RepositoryPokemonRoom
) : CoreViewModel() {

    lateinit var pokemon: Pokemon
    private val pokemonLiveData = MutableLiveData<Pokemon>()
    fun getPokemon() = pokemonLiveData

    fun loadDetailPokemon(name:String) {
        isLoading().postValue(true)
        viewModelScope.launch {
            try{
                val pokemonDetail = repositoryRetrofit.getPokemonAbilitiesAsync(name)
                when (pokemonDetail.isSuccessful) {
                    true -> {
                        isLoading().postValue(false)
                        with(pokemonDetail.body()!!) {
                            pokemonLiveData.postValue(this)
                        }
                    }
                    else -> {
                        isLoading().postValue(false)
                        getError().postValue("Error al cargar datos")
                    }
                }
            } catch (e:Exception){
                e.printStackTrace()
                isLoading().postValue(false)
                getError().postValue(e.localizedMessage)
            }
        }
    }

}
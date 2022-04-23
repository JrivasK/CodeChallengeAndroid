package com.example.codechallengeandroid.home.ui.listPokemon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengeandroid.data.Pokemon
import com.example.codechallengeandroid.databinding.ItemPokemonAdapterBinding
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ClickItemListener
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ListenerUpdatePokemon
import java.util.*
import javax.inject.Inject

class AdapterPokemon @Inject constructor() : RecyclerView.Adapter<AdapterPokemon.ViewHolder>() {
    var pokemonList: List<Pokemon> = emptyList()
    var clickItem:ClickItemListener? = null
    var updatePokemon:ListenerUpdatePokemon? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPokemonAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(pokemonList[position], position)

    override fun getItemCount(): Int = pokemonList.size

    inner class ViewHolder(private val binding: ItemPokemonAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon, position: Int) {
            binding.apply {
                pokemon.also { (name, _, _, favorite, error) ->
                    val text = when {
                        favorite -> {
                            initTimer(name, position)
                            "Favorito - $name"
                        }
                        error -> {
                            initTimer(name, position)
                            "Error - $name"
                        }
                        else -> name
                    }
                    tvNamePokemon.text = text
                    root.setOnClickListener {
                        if(clickItem!=null)
                            clickItem!!.onClick(name)
                    }
                }

            }
        }
        private fun initTimer(name:String, position: Int){
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    if(updatePokemon!=null)
                        pokemonList[position].favorite=false
                        pokemonList[position].error=false
                        updatePokemon!!.onChangeFavoriteOrError(name, position)
                }
            }, 5000)
        }
    }
}
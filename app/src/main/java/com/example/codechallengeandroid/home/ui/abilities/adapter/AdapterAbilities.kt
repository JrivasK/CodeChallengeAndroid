package com.example.codechallengeandroid.home.ui.abilities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengeandroid.databinding.ItemPokemonAdapterBinding
import com.example.codechallengeandroid.home.data.response.Ability
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ClickItemListener
import javax.inject.Inject

class AdapterAbilities @Inject constructor() : RecyclerView.Adapter<AdapterAbilities.ViewHolder>() {
    var abilities: List<Ability> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPokemonAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(abilities[position])

    override fun getItemCount(): Int = abilities.size

    inner class ViewHolder(private val binding: ItemPokemonAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(abilities: Ability) {
            binding.apply {
                abilities.also { (ability) ->
                    ability.apply {
                        tvNamePokemon.text = this!!.name
                    }
                }
            }
        }
    }
}
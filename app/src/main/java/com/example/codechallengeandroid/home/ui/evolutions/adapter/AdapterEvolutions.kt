package com.example.codechallengeandroid.home.ui.evolutions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengeandroid.databinding.ItemPokemonAdapterBinding
import com.example.codechallengeandroid.home.data.response.BaseModel
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ClickItemListener
import javax.inject.Inject

class AdapterEvolutions @Inject constructor() : RecyclerView.Adapter<AdapterEvolutions.ViewHolder>() {
    var evolutions: List<BaseModel> = emptyList()
    var clickItem:ClickItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPokemonAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(evolutions[position])

    override fun getItemCount(): Int = evolutions.size

    inner class ViewHolder(private val binding: ItemPokemonAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(evolutions: BaseModel) {
            binding.apply {
                evolutions.also { (name, _) ->
                    tvNamePokemon.text = name
                    root.setOnClickListener {
                        if (name != null) {
                            clickItem!!.onClick(name)
                        } else{
                            clickItem!!.onClick("")
                        }
                    }
                }

            }
        }
    }
}
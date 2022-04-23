package com.example.codechallengeandroid.home.data.response

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("base_happiness") val baseHappiness: Int?,
    @SerializedName("capture_rate") val captureRate: Int?,
    @SerializedName("color") val color: BaseModel?,
    @SerializedName("egg_groups") val eggGroups: List<BaseModel> = emptyList(),
    @SerializedName("evolution_chain") val evolutionChain: BaseModel?,
    @SerializedName("abilities") val abilities: List<Ability>? = emptyList(),
)

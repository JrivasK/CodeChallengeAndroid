package com.example.codechallengeandroid.home.data.response

import com.google.gson.annotations.SerializedName

data class ListPokemon(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<BaseModel>
)

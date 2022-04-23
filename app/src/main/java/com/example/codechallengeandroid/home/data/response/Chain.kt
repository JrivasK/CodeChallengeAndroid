package com.example.codechallengeandroid.home.data.response

import com.google.gson.annotations.SerializedName

data class Chain(
    @SerializedName("species") val species: BaseModel?,
    @SerializedName("evolves_to") val evolvesTo: List<Chain>
)

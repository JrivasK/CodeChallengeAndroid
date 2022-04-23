package com.example.codechallengeandroid.home.data.response

import com.google.gson.annotations.SerializedName

data class BaseModel(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)

package com.example.codechallengeandroid.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallengeandroid.data.Pokemon

open class CoreViewModel:ViewModel() {
    private val error = MutableLiveData<String>()
    fun getError() = error
    private val loading = MutableLiveData<Boolean>()
    fun isLoading() = loading
}
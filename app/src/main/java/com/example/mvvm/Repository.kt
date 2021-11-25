package com.example.mvvm

import com.example.mvvm.network.ApiService

class Repository (private val apiService: ApiService){

    fun getCharacters(page : String) = apiService.fetchCharacters(page)

}
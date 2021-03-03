package com.example.labretrofit.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit2 {
    private var urls: PokemonAPI
    var URL_BASE = "https://pokeapi.co/api/v2/"

    constructor() {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        urls = retrofit.create(PokemonAPI::class.java)
    }


    fun getService(): PokemonAPI {
        return urls
    }
}
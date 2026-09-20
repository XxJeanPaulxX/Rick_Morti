package com.example.rickmorti

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val apiService: RickMortyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(RickMortyApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickMortyApiService::class.java)
    }
}

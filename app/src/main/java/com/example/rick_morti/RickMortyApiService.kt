package com.example.rick_morti

import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyApiService {
    @GET("character")
    suspend fun getPersonajes(@Query("page") page: Int): PersonajeApiResponse

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}

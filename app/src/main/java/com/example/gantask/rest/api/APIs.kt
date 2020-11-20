package com.example.gantask.rest.api

import com.example.gantask.rest.data.BBCharactersData
import retrofit2.Response
import retrofit2.http.GET

interface APIs {

    @GET("characters")
    suspend fun getCharacters(): Response<List<BBCharactersData>>
}
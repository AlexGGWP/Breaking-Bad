package com.example.gantask.rest.fetcher

import com.example.gantask.rest.NetworkResponseHandler
import com.example.gantask.rest.data.BBCharactersData

interface BaseFetcher {
    suspend fun getAllCharacters() : NetworkResponseHandler<List<BBCharactersData>>
}
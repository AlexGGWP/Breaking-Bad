package com.example.gantask.di

import com.example.gantask.rest.NetworkResponseHandler
import com.example.gantask.rest.api.APIs
import com.example.gantask.rest.data.BBCharactersData
import com.example.gantask.rest.fetcher.BaseFetcher
import com.example.gantask.rest.handleApiError
import com.example.gantask.rest.handleSuccess
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MockTestAPIService : BaseFetcher, KoinComponent {

    val api: APIs by inject()

    override suspend fun getAllCharacters(): NetworkResponseHandler<List<BBCharactersData>> {
        return try {
            val response = api.getCharacters()
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            NetworkResponseHandler.Error(e)
        }
    }
}
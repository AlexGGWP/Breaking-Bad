package com.example.gantask.rest.fetcher

import android.content.Context
import com.example.gantask.rest.*
import com.example.gantask.rest.api.APIs
import com.example.gantask.rest.data.BBCharactersData

class CharacterFetcher(private val api: APIs, private val context: Context) : BaseFetcher {
    override suspend fun getAllCharacters(): NetworkResponseHandler<List<BBCharactersData>> {
        return if (isConnectionAvailable(context)) {
            try {
                val response = api.getCharacters()
                if (response.isSuccessful) {
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                NetworkResponseHandler.Error(e)
            }
        } else {
            noNetworkConnectivityError()
        }
    }
}

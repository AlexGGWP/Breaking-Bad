package com.example.gantask.rest

import com.example.gantask.rest.data.BBCharactersData

sealed class NetworkResponseHandler<T> {
    data class Success<T>(val successData : T) : NetworkResponseHandler<T>()
    class Error(val exception: java.lang.Exception, val message: String = "A network error has occurred!")
        : NetworkResponseHandler<List<BBCharactersData>>()
}
package com.example.gantask.di

import com.example.gantask.rest.api.APIs
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun mockNetworkForTestingModule(api: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(api)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    factory { get<Retrofit>().create(APIs::class.java) }
}
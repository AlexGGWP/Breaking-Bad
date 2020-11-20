package com.example.gantask.di

import com.example.gantask.rest.api.APIs
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideBreakingBadApi(retrofit: Retrofit): APIs {
        return retrofit.create(APIs::class.java)
    }
    single { provideBreakingBadApi(get()) }
}
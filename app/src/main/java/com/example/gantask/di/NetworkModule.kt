package com.example.gantask.di

import com.example.gantask.rest.interceptors.CachingControlInterceptor
import com.example.gantask.rest.interceptors.ForceNoCacheInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    val baseURL = "https://breakingbadapi.com/api/"

    fun provideHttpClient(): OkHttpClient? {
        val okHttpClientBuilder = forceNoCacheInterceptor()?.let {
            cachingControlInterceptor()?.let { it1 ->
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .addInterceptor(it)
                    .addNetworkInterceptor(it1)
            }
        }
        okHttpClientBuilder?.build()
        return okHttpClientBuilder?.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single {
        provideRetrofit(get(), baseURL)
    }
}

fun forceNoCacheInterceptor(): ForceNoCacheInterceptor? {
    return ForceNoCacheInterceptor()
}

fun cachingControlInterceptor(): CachingControlInterceptor? {
    return CachingControlInterceptor()
}

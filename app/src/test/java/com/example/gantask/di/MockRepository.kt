package com.example.gantask.di

import org.koin.dsl.module

val mockTestRepo = module {
    factory { MockTestAPIService() }
}
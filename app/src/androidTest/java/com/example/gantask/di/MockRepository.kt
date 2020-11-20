package com.example.gantask.di

import org.koin.dsl.module

val mockRepo = module {
    factory { MockAPIService() }
}
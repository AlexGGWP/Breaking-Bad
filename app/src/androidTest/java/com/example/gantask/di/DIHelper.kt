package com.example.gantask.di

fun generateTestAppComponent(api: String) = listOf(
    networkForTestingModule(api),
    mockRepo,
    MockWebServerInstrumentationTest
)


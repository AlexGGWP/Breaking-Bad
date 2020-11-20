package com.example.gantask.di

fun configureTestAppComponent(baseApi: String) = listOf(
    mockNetworkForTestingModule(baseApi),
    viewModelModule,
    MockWebTestServerInstrumentationTest,
    mockTestRepo
)

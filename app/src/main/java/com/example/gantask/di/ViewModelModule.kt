package com.example.gantask.di

import com.example.gantask.viewmodel.CharacterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CharacterViewModel(fetcher = get())
    }
}
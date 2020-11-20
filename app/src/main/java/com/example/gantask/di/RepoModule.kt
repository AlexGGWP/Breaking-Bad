package com.example.gantask.di

import android.content.Context
import com.example.gantask.rest.api.APIs
import com.example.gantask.rest.fetcher.BaseFetcher
import com.example.gantask.rest.fetcher.CharacterFetcher
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repoModule = module {

    fun provideBBCharactersApi(api: APIs, context: Context): BaseFetcher {
        return CharacterFetcher(api, context)
    }
    single { provideBBCharactersApi(get(), androidContext()) }

}
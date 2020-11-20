package com.example.gantask.base

import android.app.Application
import com.example.gantask.di.apiModule
import com.example.gantask.di.networkModule
import com.example.gantask.di.repoModule
import com.example.gantask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                networkModule,
                viewModelModule,
                repoModule,
                apiModule
            )
        }
    }
}
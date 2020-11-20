package com.example.gantask.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.gantask.base.BaseApplication

class CustomRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(
            cl,
            BaseApplication::class.java.name,
            context
        )
    }
}
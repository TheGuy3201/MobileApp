package com.example.apparchitectureexample;

import android.app.Application
import com.example.apparchitectureexample.di.appModules
import org.koin.core.context.startKoin

class ChapterFiveApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}
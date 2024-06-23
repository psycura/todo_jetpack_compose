package com.example.to_docompose

import android.app.Application
import com.example.to_docompose.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        stopKoin()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
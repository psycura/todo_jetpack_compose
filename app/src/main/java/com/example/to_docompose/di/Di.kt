package com.example.to_docompose.di


import org.koin.core.context.GlobalContext.startKoin

fun initKoin() {
    println("KoinModule: initKoin")

    startKoin {
        modules(appModule)
    }

}

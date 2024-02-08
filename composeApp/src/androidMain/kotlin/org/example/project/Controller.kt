package org.example.project

import android.app.Application
import koinModule.factoryModule
import koinModule.singleModule
import org.koin.core.context.startKoin

class Controller : Application() {
    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            modules(listOf(singleModule, factoryModule))
//        }
    }
}
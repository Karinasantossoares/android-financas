package com.example.desafioandroidmobills

import android.app.Application
import com.example.desafioandroidmobills.di.modulesTransaction
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(modulesTransaction)
        }
    }
}
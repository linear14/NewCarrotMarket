package com.dongldh.carrot.util

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    companion object {
        lateinit var pref: SharedPreference
        lateinit var instance: App

        fun applicationContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        pref = SharedPreference(applicationContext)
        instance = this

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

    }
}
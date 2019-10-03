package com.izanrodrigo.fintonictestchallenge.app

import android.app.Application
import com.izanrodrigo.fintonictestchallenge.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Izan on 2019-10-02.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, uiModule))
        }
    }
}
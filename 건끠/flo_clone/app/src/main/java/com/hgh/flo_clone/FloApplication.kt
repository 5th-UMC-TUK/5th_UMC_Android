package com.hgh.flo_clone

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.hgh.flo_clone.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FloApplication : Application() {

    companion object {
        lateinit var sSharedPreference: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger(
                Level.NONE
            )
            androidContext(this@FloApplication)
            modules(appModule)
        }

        sSharedPreference = applicationContext.getSharedPreferences("NEOUL", MODE_PRIVATE)

    }


}
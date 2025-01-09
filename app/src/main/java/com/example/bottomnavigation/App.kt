package com.example.bottomnavigation

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
    }
}
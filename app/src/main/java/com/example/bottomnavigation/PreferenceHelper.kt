package com.example.bottomnavigation

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences

    fun unit (context: Context) {
        sharedPreferences = context.getSharedPreferences("SHARED", Context.MODE_PRIVATE)
    }

    var isOnBoardShow: Boolean
        get() = sharedPreferences.getBoolean("onBoard", false)
        set(value) = sharedPreferences.edit().putBoolean("onBoard", value).apply()

    var isGuest: Boolean
        get() = sharedPreferences.getBoolean("guest", false)
        set(value) = sharedPreferences.edit().putBoolean("guest", value).apply()
}
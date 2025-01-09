package com.example.bottomnavigation

import android.content.Context
import android.content.SharedPreferences
import com.example.bottomnavigation.models.Notes

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences

    fun unit (context: Context) {
        sharedPreferences = context.getSharedPreferences("SHARED", Context.MODE_PRIVATE)
    }

    var text: String?
        get() = sharedPreferences.getString("text", "")
        set(value) = sharedPreferences.edit().putString("text", value).apply()
}
package com.example.bottomnavigation

import android.app.Application
import androidx.room.Room
import com.example.bottomnavigation.data.db.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        getInstance()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)

    }

    private fun getInstance(): AppDatabase? {
        if (appDatabase == null){
            appDatabase = applicationContext?.let {
                context ->
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDatabase
    }

    companion object{
        var appDatabase: AppDatabase? = null
    }
}
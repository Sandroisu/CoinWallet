package ru.slatinin.nytnews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.slatinin.nytnews.storage.AppDatabase

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        AppDatabase.createInstance(applicationContext)
    }

}

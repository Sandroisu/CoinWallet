package ru.coin.alexwallet

import android.app.Application
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import ru.coin.alexwallet.storage.AppDatabase
import kotlin.coroutines.CoroutineContext

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        AppDatabase.createInstance(applicationContext)
    }

}

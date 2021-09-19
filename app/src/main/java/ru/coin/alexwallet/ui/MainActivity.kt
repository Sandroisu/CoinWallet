package ru.coin.alexwallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coin.alexwallet.R
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import ru.coin.alexwallet.data.AppDatabase
import ru.coin.alexwallet.databinding.MainActivityBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabase.getInstance(this)
        setContentView<MainActivityBinding>(this, R.layout.main_activity)

    }
}
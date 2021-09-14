package ru.coin.alexwallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coin.alexwallet.R
import androidx.databinding.DataBindingUtil.setContentView
import ru.coin.alexwallet.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }

}
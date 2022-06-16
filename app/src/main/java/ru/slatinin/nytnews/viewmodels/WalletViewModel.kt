package ru.slatinin.nytnews.viewmodels

import androidx.lifecycle.ViewModel
import ru.slatinin.nytnews.storage.AppDatabase
import ru.slatinin.nytnews.storage.CryptoCurrency

class WalletViewModel : ViewModel() {
    private var cryptoCurrencies: List<CryptoCurrency>? = null



    suspend fun getCryptoCurrencies(): List<CryptoCurrency>? {
        return if (cryptoCurrencies == null) {
            cryptoCurrencies = AppDatabase.getInstance()?.cryptoCurrencyDao()?.getAll()
            cryptoCurrencies
        } else {
            cryptoCurrencies
        }
    }
}
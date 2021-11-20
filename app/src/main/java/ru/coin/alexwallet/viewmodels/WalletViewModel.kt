package ru.coin.alexwallet.viewmodels

import androidx.lifecycle.ViewModel
import ru.coin.alexwallet.storage.AppDatabase
import ru.coin.alexwallet.storage.CryptoCurrency

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
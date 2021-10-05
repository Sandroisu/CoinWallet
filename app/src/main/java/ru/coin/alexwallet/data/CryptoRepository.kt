package ru.coin.alexwallet.data

import ru.coin.alexwallet.storage.CryptoCurrencyDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepository @Inject constructor(private val cryptoDao: CryptoCurrencyDao) {

    fun getAllCrypto() = cryptoDao.getAll()
}
package ru.slatinin.nytnews.data

import ru.slatinin.nytnews.storage.CryptoCurrencyDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepository @Inject constructor(private val cryptoDao: CryptoCurrencyDao) {

}
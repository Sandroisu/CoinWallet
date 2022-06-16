package ru.slatinin.nytnews.storage

import androidx.room.*

@Dao
interface CryptoCurrencyDao {
    @Query("SELECT * FROM cryptocurrency")
    suspend fun getAll(): List<CryptoCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(currency: CryptoCurrency): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCrypto(currency: List<CryptoCurrency>)

    @Delete
    suspend fun deleteNews(currency: CryptoCurrency)
}
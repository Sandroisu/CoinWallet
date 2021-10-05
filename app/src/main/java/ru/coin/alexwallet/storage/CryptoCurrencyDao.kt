package ru.coin.alexwallet.storage

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoCurrencyDao {
    @Query("SELECT * FROM cryptocurrency")
    fun getAll(): Flow<List<CryptoCurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(currency: CryptoCurrency): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCrypto(currency: List<CryptoCurrency>)

    @Delete
    suspend fun deleteNews(currency: CryptoCurrency)
}
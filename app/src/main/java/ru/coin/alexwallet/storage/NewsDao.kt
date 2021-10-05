package ru.coin.alexwallet.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE id = :newsId")
    fun getNews(newsId: String): Flow<News>

    @Query("SELECT * FROM news limit 1")
    fun getLastNews(): List<News>

    @Query("SELECT EXISTS (SELECT 1 FROM news LIMIT 1)")
    suspend fun hasAnyRecord(): Int

    @Query("SELECT COUNT(*) FROM news WHERE dateInMillis > :timeToUpdate")
    suspend fun isNotTooOld(timeToUpdate: Long): Int

    @Query("SELECT * FROM news ORDER BY pubDate DESC LIMIT 20")
    suspend fun getLastTwenty(): List<News>

    @Insert
    suspend fun insertNews(news: News): Long

    @Insert
    suspend fun insertNews(news: List<News>)

    @Delete
    suspend fun deleteNews(news: News)
}
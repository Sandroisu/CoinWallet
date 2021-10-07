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

    @Query("SELECT COUNT(*) FROM news WHERE dateInMillis > :timeToUpdate and requestQuery =:query")
    suspend fun isNotTooOld(timeToUpdate: Long, query: String): Int

    @Query("SELECT * FROM news where requestQuery = :query ORDER BY pubDate DESC LIMIT 20")
    suspend fun getLastTwenty(query: String): List<News>

    @Insert
    suspend fun insertNews(news: News): Long

    @Insert
    suspend fun insertNews(news: List<News>)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("DELETE FROM news where requestQuery = :query")
    suspend fun clearTableByQuery(query: String)
}
package ru.coin.alexwallet.data

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
    fun getLastNews(): Flow<News>

    @Insert
    suspend fun insertNews(news: News): Long

    @Delete
    suspend fun deleteNews(news: News)
}
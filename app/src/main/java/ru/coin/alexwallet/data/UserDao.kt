package ru.coin.alexwallet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String): Flow<Users>

    @Insert
    suspend fun insertUser(user: Users): Long

    @Delete
    suspend fun deleteUser(user: Users)
}
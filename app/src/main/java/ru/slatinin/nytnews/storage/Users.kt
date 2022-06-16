package ru.slatinin.nytnews.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users (
    @PrimaryKey @ColumnInfo(name = "id") val userId: String,
    val name: String,
)
package ru.coin.alexwallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Users::class,
        parentColumns = ["id"],
        childColumns = ["f_userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class News(
    @PrimaryKey @ColumnInfo(name = "id") val newsId: String,
    val f_userId: String,
    val title: String,
    val article: String,
    val imageUrl: String = ""

)
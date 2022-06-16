package ru.slatinin.nytnews.storage

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
    val topic: String,
    val leadParagraph: String,
    val newsUrl: String,
    val imageUrl: String,
    val newsImageSubType: String,
    val pubDate: String,
    val requestQuery: String,
    val dateInMillis: Long
)
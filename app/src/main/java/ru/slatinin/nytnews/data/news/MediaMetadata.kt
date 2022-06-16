package ru.slatinin.nytnews.data.news

import com.google.gson.annotations.SerializedName

data class MediaMetadata(
    @field:SerializedName("url") val mediaUrl: String,
    @field:SerializedName("format") val format: String
)

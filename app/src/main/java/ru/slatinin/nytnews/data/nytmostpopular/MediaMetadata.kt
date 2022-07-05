package ru.slatinin.nytnews.data.nytmostpopular

import com.google.gson.annotations.SerializedName

data class MediaMetadata(
    @field:SerializedName("url") val mediaUrl: String,
    @field:SerializedName("format") val format: String
)

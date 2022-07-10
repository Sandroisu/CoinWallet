package ru.slatinin.nytnews.data.nytmostpopular

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class MediaMetadata(
    @field:SerializedName("url") val mediaUrl: String,
    @field:SerializedName("format") val format: String
)

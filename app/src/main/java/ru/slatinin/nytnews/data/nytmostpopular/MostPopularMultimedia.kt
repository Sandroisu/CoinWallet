package ru.slatinin.nytnews.data.nytmostpopular

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
const val STANDARD_THUMB_CONST = "mediumThreeByTwo210"
@Keep
data class MostPopularMultimedia(
    @field:SerializedName("media-metadata") val mostPopularMediaMetaData: List<MediaMetadata>,
)

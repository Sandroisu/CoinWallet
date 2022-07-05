package ru.slatinin.nytnews.data.nytmostpopular

import com.google.gson.annotations.SerializedName
const val STANDARD_THUMB_CONST = "mediumThreeByTwo210"
data class MostPopularMultimedia(
    @field:SerializedName("media-metadata") val mostPopularMediaMetaData: List<MediaMetadata>,
)

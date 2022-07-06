package ru.slatinin.nytnews.data.nytsections

import com.google.gson.annotations.SerializedName

const val NYT_SECTION_THUMB_CONST = "threeByTwoSmallAt2X"

data class NytSectionMultimedia(
    @field:SerializedName("url") val url: String,
    @field:SerializedName("format") val format: String
)

package ru.slatinin.nytnews.data.nytsections

import com.google.gson.annotations.SerializedName

data class SectionResult(
    @field:SerializedName("section") val section: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("abstract") val abstract: String,
    @field:SerializedName("multimedia") val multimedia: List<NytSectionMultimedia>
)
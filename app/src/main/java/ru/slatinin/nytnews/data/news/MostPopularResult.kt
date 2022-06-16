package ru.slatinin.nytnews.data.news

import com.google.gson.annotations.SerializedName
import ru.slatinin.nytnews.data.viewdata.NewsItem

data class MostPopularResult(
    @field:SerializedName("section") val section: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("abstract") val abstract: String,
    @field:SerializedName("media") val multimedia: List<MostPopularMultimedia>
)

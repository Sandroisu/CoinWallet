package ru.slatinin.nytnews.data.viewdata

import com.google.gson.annotations.SerializedName
import ru.slatinin.nytnews.data.news.MostPopularMultimedia

data class NewsItem(
    @field:SerializedName("lead_paragraph") val leadParagraph: String,
    @field:SerializedName("abstract") val topic: String,
    @field:SerializedName("web_url") val newsUrl: String,
    @field:SerializedName("published_date") val pubDate: String,
    @field:SerializedName("media") val multimedia: List<MostPopularMultimedia>,
)
package ru.coin.alexwallet.data

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @field:SerializedName("lead_paragraph") val leadParagraph: String,
    @field:SerializedName("abstract") val topic: String,
    @field:SerializedName("web_url") val newsUrl: String,
    @field:SerializedName("pub_date") val pubDate: String,
    @field:SerializedName("multimedia") val multimedia: List<NewsMultimedia>,
)
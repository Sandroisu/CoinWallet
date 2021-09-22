package ru.coin.alexwallet.data

import com.google.gson.annotations.SerializedName

data class NewsItem (
    @field:SerializedName("lead_paragraph") val topic: String,
    @field:SerializedName("abstract") val leadParagraph: String,
    @field:SerializedName("web_url") val newsUrl: String,
    @field:SerializedName("multimedia") val multimedia: List<NewsMultimedia>
)
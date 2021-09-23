package ru.coin.alexwallet.data

import com.google.gson.annotations.SerializedName

data class NewsResponseResults(
    @field:SerializedName("docs") val docs: List<NewsItem>)

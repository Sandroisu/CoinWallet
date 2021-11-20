package ru.coin.alexwallet.data.news

import com.google.gson.annotations.SerializedName
import ru.coin.alexwallet.data.viewdata.NewsItem

data class NewsResponseResults(
    @field:SerializedName("docs") val docs: List<NewsItem>)

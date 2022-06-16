package ru.slatinin.nytnews.data.news

import com.google.gson.annotations.SerializedName
import ru.slatinin.nytnews.data.viewdata.NewsItem

data class NewsResponseResults(
    @field:SerializedName("docs") val docs: List<NewsItem>)

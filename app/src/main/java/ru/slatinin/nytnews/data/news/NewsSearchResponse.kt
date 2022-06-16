package ru.slatinin.nytnews.data.news

import com.google.gson.annotations.SerializedName

data class NewsSearchResponse(
    @field:SerializedName("results") val results: List<MostPopularResult>
)

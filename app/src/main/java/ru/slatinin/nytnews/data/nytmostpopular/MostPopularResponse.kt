package ru.slatinin.nytnews.data.nytmostpopular

import com.google.gson.annotations.SerializedName

data class MostPopularResponse(
    @field:SerializedName("results") val results: List<MostPopularResult>,
    @field:SerializedName("num_results") val numResults: Int?
)

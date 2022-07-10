package ru.slatinin.nytnews.data.nytmostpopular

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class MostPopularResponse(
    @field:SerializedName("results") val results: List<MostPopularResult>,
    @field:SerializedName("num_results") val numResults: Int?
)

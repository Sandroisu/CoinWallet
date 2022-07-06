package ru.slatinin.nytnews.data.nytsections

import com.google.gson.annotations.SerializedName

data class SectionResponse(
    @field:SerializedName("results") val results: List<SectionResult>,
    @field:SerializedName("num_results") val numResults: Int?
)

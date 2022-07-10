package ru.slatinin.nytnews.data.nytsections

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SectionResponse(
    @field:SerializedName("results") val results: List<SectionResult>,
    @field:SerializedName("num_results") val numResults: Int?
)

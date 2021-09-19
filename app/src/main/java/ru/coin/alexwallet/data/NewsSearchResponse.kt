package ru.coin.alexwallet.data

import com.google.gson.annotations.SerializedName

data class NewsSearchResponse (
    @field:SerializedName("response") val results: NewsResponseResults
)
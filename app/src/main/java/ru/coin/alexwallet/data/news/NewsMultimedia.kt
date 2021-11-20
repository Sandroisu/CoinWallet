package ru.coin.alexwallet.data.news

import com.google.gson.annotations.SerializedName
const val SUBTYPE = "square320"
data class NewsMultimedia(
    @field:SerializedName("url") val imageUrl: String,
    @field:SerializedName("subtype") val newsImageSubtype: String,
)

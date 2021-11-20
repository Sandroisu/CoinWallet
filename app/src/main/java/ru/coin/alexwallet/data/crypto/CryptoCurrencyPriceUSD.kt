package ru.coin.alexwallet.data.crypto

import com.google.gson.annotations.SerializedName

data class CryptoCurrencyPriceUSD(
    @field:SerializedName("price") val priceUSD: String,
    @field:SerializedName("percent_change_1h") val percentChange: String,
)

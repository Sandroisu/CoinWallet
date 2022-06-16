package ru.slatinin.nytnews.data.crypto

import com.google.gson.annotations.SerializedName

data class CryptoCurrencyPrice(
    @field:SerializedName("USD") val price: CryptoCurrencyPriceUSD,
)

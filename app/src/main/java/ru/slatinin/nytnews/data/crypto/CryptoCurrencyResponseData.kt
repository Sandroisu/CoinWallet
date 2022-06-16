package ru.slatinin.nytnews.data.crypto

import com.google.gson.annotations.SerializedName

data class CryptoCurrencyResponseData(
    @field:SerializedName("1") val first: CryptoCurrencyInfo
)

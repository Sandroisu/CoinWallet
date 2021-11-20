package ru.coin.alexwallet.data.crypto

import com.google.gson.annotations.SerializedName

data class CryptoCurrencyResponseData(
    @field:SerializedName("1") val first: CryptoCurrencyInfo
)

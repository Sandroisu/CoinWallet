package ru.coin.alexwallet.data.crypto

import com.google.gson.annotations.SerializedName

data class CryptoCurrencyResponse(
    @field:SerializedName("data") val data: CryptoCurrencyResponseData
)
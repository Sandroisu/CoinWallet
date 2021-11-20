package ru.coin.alexwallet.data.crypto

import com.google.gson.annotations.SerializedName

data class CryptoCurrencyInfo(
    @field:SerializedName("id") val remoteId: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("symbol") val symbol: String,
    @field:SerializedName("quote") val price: CryptoCurrencyPrice,

)

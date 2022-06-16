package ru.slatinin.nytnews.data.viewdata

data class CryptoItem(
    val name: String,
    val marketPriceInteger: Long,
    val marketPriceDecimal: Long,
    val walletValueInteger: Long,
    val walletValueDecimal: Long,
    val dbId: Long,
    val imageResId: Int
)
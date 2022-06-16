package ru.slatinin.nytnews.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoCurrency(
    val name: String,
    val remoteId: Int,
    val marketPriceInteger: Long,
    val marketPriceDecimal: Long,
    val walletValueInteger: Long,
    val walletValueDecimal: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var cryptoId: Long = 0
}

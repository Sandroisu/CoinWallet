package ru.coin.alexwallet.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CryptoCurrency(
    val name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var cryptoId: Long = 0
}

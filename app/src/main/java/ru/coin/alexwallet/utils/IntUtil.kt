package ru.coin.alexwallet.utils

import java.lang.NumberFormatException

class IntUtil {

    companion object {
        fun parseIntegerOrGetZero(value: String): Int {
            return try {
                value.toInt()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0
            }
        }
    }
}
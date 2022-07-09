package ru.slatinin.nytnews.data.nytapi

import java.util.*

interface NytResult {
    fun getResultTitle(): String
    fun getMultimediaUrl(): String
    fun getAbstractText(): String
    fun getNewsUrl(): String
    fun isEqual (obj: Any?): Boolean
}
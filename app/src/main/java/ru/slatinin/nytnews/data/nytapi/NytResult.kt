package ru.slatinin.nytnews.data.nytapi

interface NytResult {
    fun getResultTitle():String
    fun getMultimediaItems():
}
package ru.slatinin.nytnews.data.nytmostpopular

import com.google.gson.annotations.SerializedName
import ru.slatinin.nytnews.data.nytapi.NytResult

data class MostPopularResult(
    @field:SerializedName("section") val section: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("abstract") val abstract: String,
    @field:SerializedName("media") val multimedia: List<MostPopularMultimedia>
) : NytResult{
    override fun getResultTitle(): String {
        TODO("Not yet implemented")
    }

    override fun getMultimediaItems(): ??? {
        TODO("Not yet implemented")
    }
}

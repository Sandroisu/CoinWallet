package ru.slatinin.nytnews.data.nytmostpopular

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.data.nytapi.NytResult
@Keep
data class MostPopularResult(
    @field:SerializedName("section") val section: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("abstract") val abstract: String,
    @field:SerializedName("media") val multimedia: List<MostPopularMultimedia>
) : NytResult {
    override fun getResultTitle(): String {
        return title
    }

    override fun getMultimediaUrl(): String {
        if (multimedia.isEmpty() || multimedia[0].mostPopularMediaMetaData.isEmpty()) {
            return ""
        }
        var normalMedia = multimedia[0].mostPopularMediaMetaData[0]
        for (media in multimedia[0].mostPopularMediaMetaData) {
            if (media.format == STANDARD_THUMB_CONST) {
                normalMedia = media
                break
            }
        }
        return normalMedia.mediaUrl
    }

    override fun getAbstractText(): String {
        return abstract
    }

    override fun getNewsUrl(): String {
        return url
    }

    override fun isEqual(obj: Any?): Boolean {
        return this == obj
    }
}

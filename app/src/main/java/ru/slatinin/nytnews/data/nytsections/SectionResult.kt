package ru.slatinin.nytnews.data.nytsections

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.data.nytapi.NytResult
@Keep
data class SectionResult(
    @field:SerializedName("section") val section: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("abstract") val abstract: String,
    @field:SerializedName("multimedia") val multimedia: List<NytSectionMultimedia>
) : NytResult {
    override fun getResultTitle(): String {
        return title
    }

    override fun getMultimediaUrl(): String {
        if (multimedia.isEmpty() || multimedia[0].url.isEmpty()) {
            return ""
        }
        var normalMedia = multimedia[0].url
        for (media in multimedia) {
            if (media.format == NYT_SECTION_THUMB_CONST) {
                normalMedia = media.url
                break
            }
        }
        return normalMedia
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
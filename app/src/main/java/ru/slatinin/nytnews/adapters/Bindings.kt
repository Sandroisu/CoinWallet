package ru.slatinin.nytnews.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.data.news.MostPopularMultimedia
import ru.slatinin.nytnews.data.news.STANDARD_THUMB_CONST

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, multimedia: List<MostPopularMultimedia>) {

    if (multimedia.isEmpty() || multimedia[0].mostPopularMediaMetaData.isEmpty()) {
        view.setImageResource(R.drawable.ic_recommended)
        return
    }
    val radius = view.context.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
    var normalMedia = multimedia[0].mostPopularMediaMetaData[0]
    for (media in multimedia[0].mostPopularMediaMetaData) {
        if (media.format == STANDARD_THUMB_CONST) {
            normalMedia = media
            break
        }
    }
    Glide.with(view.context)
        .load(normalMedia.mediaUrl)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transform(CenterInside(), RoundedCorners(radius))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)

}
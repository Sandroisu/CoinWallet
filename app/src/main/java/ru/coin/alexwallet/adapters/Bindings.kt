package ru.coin.alexwallet.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.coin.alexwallet.R
import ru.coin.alexwallet.data.news.NewsMultimedia
import ru.coin.alexwallet.data.news.SUBTYPE

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, multimedia: List<NewsMultimedia>) {

    if (multimedia.isEmpty()) {
        view.setImageResource(R.drawable.ic_news)
        return
    }
    val radius = view.context.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
    var normalMedia = multimedia[0]
    for (media in multimedia) {
        if (media.newsImageSubtype == SUBTYPE) {
            normalMedia = media
        }
    }
    Glide.with(view.context)
        .load("https://static01.nyt.com/${normalMedia.imageUrl}")
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transform(CenterInside(), RoundedCorners(radius))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)

}
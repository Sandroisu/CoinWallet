package ru.slatinin.nytnews.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.data.RssReader
import ru.slatinin.nytnews.data.nytmostpopular.MostPopularMultimedia
import ru.slatinin.nytnews.data.nytmostpopular.STANDARD_THUMB_CONST
import ru.slatinin.nytnews.data.nytsections.NYT_SECTION_THUMB_CONST
import ru.slatinin.nytnews.data.nytsections.NytSectionMultimedia

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, url: String) {

    if (url.isEmpty()) {
        view.setImageResource(R.drawable.ic_recommended)
        return
    }
    val radius = view.context.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
    Glide.with(view.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .transform(CenterInside(), RoundedCorners(radius))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

@BindingAdapter("imageFromRssUrl")
fun bindImageFromRss(view: ImageView, rssItem: RssReader.Item) {
    if (rssItem.imageUrl == null) {
        view.setImageResource(R.drawable.ic_recommended)
        return
    }
    rssItem.imageUrl?.let {
        if (it.isEmpty()) {
            view.setImageResource(R.drawable.ic_recommended)
            return
        }
        val radius = view.context.resources.getDimensionPixelSize(R.dimen.image_corner_radius)
        Glide.with(view.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transform(CenterInside(), RoundedCorners(radius))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

}
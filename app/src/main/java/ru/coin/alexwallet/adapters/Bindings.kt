package ru.coin.alexwallet.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.coin.alexwallet.data.NewsMultimedia

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, multimedia: List<NewsMultimedia>) {
    if (multimedia.isNotEmpty()) {
        Glide.with(view.context)
            .load("https://static01.nyt.com/${multimedia[0].imageUrl}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}
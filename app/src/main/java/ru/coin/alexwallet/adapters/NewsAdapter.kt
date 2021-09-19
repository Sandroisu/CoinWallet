package ru.coin.alexwallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.coin.alexwallet.data.News
import ru.coin.alexwallet.data.NewsItem
import ru.coin.alexwallet.databinding.NewsListItemBinding

class NewsAdapter : PagingDataAdapter<NewsItem, RecyclerView.ViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        (holder as NewsViewHolder).bind(news)
    }

    class NewsViewHolder(
        private val binding: NewsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.news?.let { news ->

                }
            }
        }

        fun bind(item: NewsItem?) {
            binding.apply {
                news = item
                executePendingBindings()
            }
        }
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<NewsItem>() {

        override fun areItemsTheSame(old: NewsItem, aNew: NewsItem): Boolean {
            return old.topic == aNew.topic
        }

        override fun areContentsTheSame(old: NewsItem, aNew: NewsItem): Boolean {
            return old == aNew
        }
    }
}


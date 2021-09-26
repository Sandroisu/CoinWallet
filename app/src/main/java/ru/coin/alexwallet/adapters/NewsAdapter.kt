package ru.coin.alexwallet.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
            binding.setClickListener {
                binding.news?.let { news ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.newsUrl))
                    ContextCompat.startActivity(binding.root.context, intent, null)
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
            return old.leadParagraph == aNew.leadParagraph
        }

        override fun areContentsTheSame(old: NewsItem, aNew: NewsItem): Boolean {
            return old == aNew
        }
    }
}


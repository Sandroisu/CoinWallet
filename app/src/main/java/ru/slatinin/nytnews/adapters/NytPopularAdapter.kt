package ru.slatinin.nytnews.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.slatinin.nytnews.data.nytmostpopular.MostPopularResult
import ru.slatinin.nytnews.databinding.NewsListItemBinding

class NytPopularAdapter :
    PagingDataAdapter<MostPopularResult, RecyclerView.ViewHolder>(NewsDiffCallback()) {

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
                binding.mostPopularNews?.let { mostPopularItem ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mostPopularItem.url))
                    ContextCompat.startActivity(binding.root.context, intent, null)
                }
            }
        }

        fun bind(item: MostPopularResult?) {
            binding.apply {
                mostPopularNews = item
                executePendingBindings()
            }
        }
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<MostPopularResult>() {

        override fun areItemsTheSame(old: MostPopularResult, aNew: MostPopularResult): Boolean {

            return old.abstract == aNew.abstract
        }

        override fun areContentsTheSame(
            old: MostPopularResult,
            aNew: MostPopularResult
        ): Boolean {
            return old == aNew
        }
    }
}


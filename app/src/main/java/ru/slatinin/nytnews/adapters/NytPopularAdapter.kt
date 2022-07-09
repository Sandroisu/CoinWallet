package ru.slatinin.nytnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.slatinin.nytnews.data.nytapi.NytResult
import ru.slatinin.nytnews.databinding.ItemMostPopularBinding

class NytPopularAdapter(private val browseLinkCallback: BrowseLinkCallback) :
    PagingDataAdapter<NytResult, RecyclerView.ViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemMostPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), browseLinkCallback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        (holder as NewsViewHolder).bind(news)
    }

    class NewsViewHolder(
        private val binding: ItemMostPopularBinding,
        private val browseLinkCallback: BrowseLinkCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.mostPopularNews?.let { nytResult ->
                    browseLinkCallback.onLoadLink(nytResult.getNewsUrl())
                }
            }
        }

        fun bind(item: NytResult?) {
            binding.apply {
                mostPopularNews = item
                executePendingBindings()
            }
        }
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<NytResult>() {

        override fun areItemsTheSame(old: NytResult, aNew: NytResult): Boolean {

            return old.getAbstractText() == aNew.getAbstractText()
        }

        override fun areContentsTheSame(
            old: NytResult,
            aNew: NytResult
        ): Boolean {

            return old.isEqual(aNew)
        }
    }
}


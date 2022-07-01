package ru.slatinin.nytnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.slatinin.nytnews.data.models.SectionItem
import ru.slatinin.nytnews.databinding.ItemRtRssBinding
import ru.slatinin.nytnews.databinding.ItemSectionBinding

class SectionsAdapter(
    private val sectionItems: ArrayList<SectionItem>,
    private val listener: SectionClickListener
) : RecyclerView.Adapter<SectionsAdapter.SectionHolder>() {


    inner class SectionHolder(
        private val binding: ItemSectionBinding,
        private val listener: SectionClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        var sectionItem: SectionItem? = null

        init {
            binding.root.setOnClickListener {
                sectionItem?.let {
                    listener.onSectionClick(it)
                }
            }
        }

        fun bind(sectionItem: SectionItem) {
            this.sectionItem = sectionItem
            binding.itemSectionIcon.setImageResource(sectionItem.sectionIcon)
            binding.itemSectionText.text = sectionItem.sectionName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionHolder {
        val binding = ItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SectionHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: SectionHolder, position: Int) {
        holder.bind(sectionItems[position])
    }

    override fun getItemCount(): Int {
        return sectionItems.size
    }
}
package ru.coin.alexwallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.coin.alexwallet.data.viewdata.CryptoItem
import ru.coin.alexwallet.databinding.CryptoListItemBinding

class CryptoAdapter(private val cryptoList: List<CryptoItem>, private val clickCallback: CryptoItemClickCallback) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {


    class CryptoViewHolder(
        private val binding: CryptoListItemBinding,
        private val clickCallback: CryptoItemClickCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CryptoItem?) {
            binding.apply {
                crypto = item
                executePendingBindings()
            }
            item?.let { binding.cryptoListItemImage.setImageResource(it.imageResId) }
            item?.let {
                val name = it.name
                binding.cryptoListItemImage.setOnClickListener {
                    clickCallback.onItemClick(name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(
            CryptoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickCallback
        )
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoList[position])
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

}

interface CryptoItemClickCallback{
    fun onItemClick(name: String)
}
package ru.slatinin.nytnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.slatinin.nytnews.data.viewdata.CryptoItem
import ru.slatinin.nytnews.databinding.CryptoListItemBinding

class CryptoWalletAdapter (private val cryptoList: List<CryptoItem>, private val clickCallback: CryptoWalletItemClickCallback) :
    RecyclerView.Adapter<CryptoWalletAdapter.CryptoWalletViewHolder>() {


    class CryptoWalletViewHolder(
        private val binding: CryptoListItemBinding,
        private val clickCallback: CryptoWalletItemClickCallback
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
                    clickCallback.onWalletItemClick(name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoWalletViewHolder {
        return CryptoWalletViewHolder(
            CryptoListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickCallback
        )
    }

    override fun onBindViewHolder(holder: CryptoWalletViewHolder, position: Int) {
        holder.bind(cryptoList[position])
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

}

interface CryptoWalletItemClickCallback{
    fun onWalletItemClick(name: String)
}
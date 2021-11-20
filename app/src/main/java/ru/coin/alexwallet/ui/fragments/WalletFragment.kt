package ru.coin.alexwallet.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.coin.alexwallet.adapters.CryptoWalletAdapter
import ru.coin.alexwallet.adapters.CryptoWalletItemClickCallback
import ru.coin.alexwallet.data.viewdata.CryptoItem
import ru.coin.alexwallet.databinding.FragmentWalletBinding
import ru.coin.alexwallet.viewmodels.WalletViewModel

class WalletFragment : Fragment() {

    private val viewModel: WalletViewModel by viewModels()
    private lateinit var binding: FragmentWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        launchCryptoCurrencyQuery()
        return binding.root
    }

    private fun launchCryptoCurrencyQuery() {
        lifecycleScope.launch {
            val cryptoItems = ArrayList<CryptoItem>()
            viewModel.getCryptoCurrencies()?.forEach {
                val resId = resources.getIdentifier(
                    it.name,
                    "drawable",
                    context?.applicationContext?.packageName
                )
                val cryptoItem = CryptoItem(
                    name = it.name,
                    dbId = it.cryptoId,
                    imageResId = resId,
                    marketPriceInteger = it.marketPriceInteger,
                    marketPriceDecimal = it.marketPriceDecimal,
                    walletValueInteger = it.walletValueInteger,
                    walletValueDecimal = it.walletValueDecimal
                )
                cryptoItems.add(cryptoItem)
            }
            val itemClickCallback = object : CryptoWalletItemClickCallback {
                override fun onWalletItemClick(name: String) {

                }
            }
            binding.fragmentWalletCryptoCurrencyList.adapter =
                CryptoWalletAdapter(cryptoItems, itemClickCallback)
        }
    }

}
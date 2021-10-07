package ru.coin.alexwallet.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.coin.alexwallet.adapters.CryptoAdapter
import ru.coin.alexwallet.adapters.CryptoItemClickCallback
import ru.coin.alexwallet.adapters.NewsAdapter
import ru.coin.alexwallet.data.viewdata.CryptoItem
import ru.coin.alexwallet.databinding.FragmentNewsBinding
import ru.coin.alexwallet.utils.ConnectionUtil
import ru.coin.alexwallet.viewmodels.NewsViewModel

const val DEFAULT_QUERY = "crypto"

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val newsAdapter = NewsAdapter()
    private val viewModel: NewsViewModel by viewModels()
    private var recommendedNewsJob: Job? = null
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val orientation = activity?.resources?.configuration?.orientation
        val newsLayoutManager: LinearLayoutManager
        val cryptoLayoutManager = LinearLayoutManager(context)
        if (Configuration.ORIENTATION_PORTRAIT == orientation) {
            newsLayoutManager = object : LinearLayoutManager(context) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    lp?.width = (width / 1.5).toInt()
                    return true
                }
            }
            newsLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            cryptoLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        } else {
            newsLayoutManager = LinearLayoutManager(context)
            newsLayoutManager.orientation = LinearLayoutManager.VERTICAL
            cryptoLayoutManager.orientation = LinearLayoutManager.VERTICAL
        }
        binding.fragmentNewsRecommendedHorizontal.layoutManager = newsLayoutManager
        binding.fragmentNewsRecommendedHorizontal.adapter = newsAdapter
        binding.fragmentNewsRecommendedVertical?.adapter = newsAdapter
        binding.fragmentNewsCryptoCurrencyList.layoutManager = cryptoLayoutManager
        lifecycleScope.launch {
            newsAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.fragmentNewsProgress.isVisible = loadStates.refresh is LoadState.Loading
                binding.fragmentNewsError.isVisible = loadStates.refresh is LoadState.Error
            }
        }
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    search(DEFAULT_QUERY)
                }
            })
        }
        context?.let {
            binding.fragmentNewsError.isVisible = ConnectionUtil.isOnline(it)
            if (ConnectionUtil.isOnline(it)) {
                search(DEFAULT_QUERY)
            }
        }
        getCryptoItems()

        return binding.root
    }

    private fun getCryptoItems() {
        lifecycleScope.launch {
            val cryptoItems = ArrayList<CryptoItem>()
            viewModel.getCryptoCurrencies()?.forEach {
                val resId = resources.getIdentifier(
                    it.name,
                    "drawable",
                    context?.applicationContext?.packageName
                )
                val cryptoItem = CryptoItem(name = it.name, dbId = it.cryptoId, imageResId = resId)
                cryptoItems.add(cryptoItem)
            }
            val itemClickCallback = object : CryptoItemClickCallback {
                override fun onItemClick(name: String) {
                    search(name)
                }
            }
            binding.fragmentNewsCryptoCurrencyList.adapter =
                CryptoAdapter(cryptoItems, itemClickCallback)
        }
    }

    private fun search(query: String) {
        recommendedNewsJob?.cancel()
        recommendedNewsJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }

}
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
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.coin.alexwallet.R
import ru.coin.alexwallet.adapters.NewsAdapter
import ru.coin.alexwallet.databinding.FragmentNewsBinding
import ru.coin.alexwallet.utils.ConnectionUtil
import ru.coin.alexwallet.viewmodels.NewsViewModel

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val newsAdapter = NewsAdapter()
    private val viewModel: NewsViewModel by viewModels()
    private var recommendedNewsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val orientation = activity?.resources?.configuration?.orientation
        val lm: LinearLayoutManager
        if (Configuration.ORIENTATION_PORTRAIT == orientation) {
            lm = object : LinearLayoutManager(context) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    lp?.width = (width / 1.5).toInt()
                    return true
                }
            }
            lm.orientation = LinearLayoutManager.HORIZONTAL
        } else {
            lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL
        }
        binding.fragmentNewsRecommendedHorizontal.layoutManager = lm
        binding.fragmentNewsRecommendedHorizontal.adapter = newsAdapter
        binding.fragmentNewsRecommendedVertical?.adapter = newsAdapter
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
                    search()
                }
            })
        }
        context?.let {
            binding.fragmentNewsError.isVisible = ConnectionUtil.isOnline(it)
            if (ConnectionUtil.isOnline(it)) {
                search()
            }
        }
        return binding.root
    }

    private fun search() {
        recommendedNewsJob?.cancel()
        recommendedNewsJob = lifecycleScope.launch {
            viewModel.searchPictures().collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }

}
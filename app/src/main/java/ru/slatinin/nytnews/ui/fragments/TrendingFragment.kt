package ru.slatinin.nytnews.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.slatinin.nytnews.adapters.BrowseLinkCallback
import ru.slatinin.nytnews.adapters.NytPopularAdapter
import ru.slatinin.nytnews.adapters.RssAdapter
import ru.slatinin.nytnews.databinding.FragmentTrendingBinding
import ru.slatinin.nytnews.utils.ConnectionUtil
import ru.slatinin.nytnews.viewmodels.NewsViewModel


@AndroidEntryPoint
class TrendingFragment : Fragment() {
    private var nytAdapter: NytPopularAdapter? = null
    private var euroRssAdapter: RssAdapter? = null
    private var rtRssAdapter: RssAdapter? = null
    private var nytJob: Job? = null
    private var euronewsJob: Job? = null
    private var rtJob: Job? = null
    private lateinit var binding: FragmentTrendingBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        context ?: return binding.root

        if (euroRssAdapter == null) {
            euroRssAdapter = RssAdapter(object : BrowseLinkCallback {
                override fun onLoadLink(url: String) {
                    findNavController().navigate(TrendingFragmentDirections.trendingToBrowser(url))
                }
            })
        }
        if (rtRssAdapter == null) {
            rtRssAdapter = RssAdapter(object : BrowseLinkCallback {
                override fun onLoadLink(url: String) {
                    findNavController().navigate(TrendingFragmentDirections.trendingToBrowser(url))
                }
            })
        }
        if(nytAdapter == null){
            nytAdapter = NytPopularAdapter(object : BrowseLinkCallback {
                override fun onLoadLink(url: String) {
                    findNavController().navigate(TrendingFragmentDirections.trendingToBrowser(url))
                }
            })
        }
        binding.nytList.layoutManager = getLayoutManager()
        binding.euronewsList.layoutManager = getLayoutManager()
        binding.rtList.layoutManager = getLayoutManager()

        binding.nytList.adapter = nytAdapter
        binding.euronewsList.adapter = euroRssAdapter
        binding.rtList.adapter = rtRssAdapter
        lifecycleScope.launch {
            nytAdapter?.let {
                it.loadStateFlow.collectLatest { loadStates ->
                    binding.popularProgress.isVisible = loadStates.refresh is LoadState.Loading
                    binding.popularByViews.isVisible =
                        loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
                    binding.popularError.isVisible = loadStates.refresh is LoadState.Error
                }
            }
        }
        lifecycleScope.launch {
            euroRssAdapter?.let {
                it.loadStateFlow.collectLatest { loadStates ->
                    binding.popularByEmails.isVisible =
                        loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
                }
            }
        }
        lifecycleScope.launch {
            rtRssAdapter?.let {
                it.loadStateFlow.collectLatest { loadStates ->
                    binding.popularByShares.isVisible =
                        loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
                }
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
            binding.popularError.isVisible = ConnectionUtil.isOnline(it)
            if (ConnectionUtil.isOnline(it)) {
                search()
            }
        }

        binding.popularRefresh.setOnRefreshListener {
            refresh()
            binding.popularRefresh.isRefreshing = false
        }
        return binding.root
    }

    private fun refresh() {
        nytJob?.cancel()
        euronewsJob?.cancel()
        rtJob?.cancel()
        viewModel.nullAllResults()
        search()
    }


    private fun search() {

        nytJob?.cancel()
        nytJob = lifecycleScope.launch {
            viewModel.loadPopularByViews("viewed").collectLatest {
                nytAdapter?.submitData(it)
            }
        }
        euronewsJob?.cancel()
        euronewsJob = lifecycleScope.launch {
            viewModel.loadEuroRssFeed("https://www.euronews.com/rss?format=mrss&level=theme&name=news")
                .collectLatest {
                    euroRssAdapter?.submitData(it)
                }
        }
        rtJob?.cancel()
        rtJob = lifecycleScope.launch {
            viewModel.loadRtRssFeed("https://www.rt.com/rss/").collectLatest {
                rtRssAdapter?.submitData(it)
            }
        }
    }

    private fun getLayoutManager(): LinearLayoutManager {
        val layoutManager = object : LinearLayoutManager(context) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.width = (width / 1.5).toInt()
                return true
            }
        }
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        return layoutManager
    }
}
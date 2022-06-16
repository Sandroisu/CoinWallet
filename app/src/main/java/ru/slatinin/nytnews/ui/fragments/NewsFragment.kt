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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.slatinin.nytnews.adapters.MostPopularNewsAdapter
import ru.slatinin.nytnews.databinding.FragmentPopularBinding
import ru.slatinin.nytnews.utils.ConnectionUtil
import ru.slatinin.nytnews.viewmodels.NewsViewModel


@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val viewedType = "viewed"
    private val emailedType = "emailed"
    private val sharedType = "shared"
    private val mostPopularByViewsAdapter = MostPopularNewsAdapter()
    private val mostPopularByEmailsAdapter = MostPopularNewsAdapter()
    private val mostPopularBySharedAdapter = MostPopularNewsAdapter()
    private val viewModel: NewsViewModel by viewModels()
    private var popularByViewsJob: Job? = null
    private var popularByEmailsJob: Job? = null
    private var popularBySharedJob: Job? = null
    private lateinit var binding: FragmentPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        context ?: return binding.root


        binding.popularByViewsList.layoutManager = getLayoutManager()
        binding.popularByEmailsList.layoutManager = getLayoutManager()
        binding.popularBySharedList.layoutManager = getLayoutManager()

        binding.popularByViewsList.adapter = mostPopularByViewsAdapter
        binding.popularByEmailsList.adapter = mostPopularByEmailsAdapter
        binding.popularBySharedList.adapter = mostPopularBySharedAdapter
        lifecycleScope.launch {
            mostPopularByViewsAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.popularProgress.isVisible = loadStates.refresh is LoadState.Loading
                binding.popularByViews.isVisible = loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
                binding.popularError.isVisible = loadStates.refresh is LoadState.Error
            }
        }
        lifecycleScope.launch {
            mostPopularByEmailsAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.popularByEmails.isVisible = loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
            }
        }
        lifecycleScope.launch {
            mostPopularBySharedAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.popularByShares.isVisible = loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
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
        return binding.root
    }


    private fun search() {

        popularByViewsJob?.cancel()
        popularByViewsJob = lifecycleScope.launch {
            viewModel.loadPopularByViews(viewedType).collectLatest {
                mostPopularByViewsAdapter.submitData(it)
            }
        }
        popularByEmailsJob?.cancel()
        popularByEmailsJob = lifecycleScope.launch {
            viewModel.loadPopularByEmails(emailedType).collectLatest {
                mostPopularByEmailsAdapter.submitData(it)
            }
        }
        popularBySharedJob?.cancel()
        popularBySharedJob = lifecycleScope.launch {
            viewModel.loadPopularByShared(sharedType).collectLatest {
                mostPopularBySharedAdapter.submitData(it)
            }
        }
    }

    fun getLayoutManager(): LinearLayoutManager {
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
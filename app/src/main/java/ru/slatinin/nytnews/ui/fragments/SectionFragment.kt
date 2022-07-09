package ru.slatinin.nytnews.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import ru.slatinin.nytnews.databinding.FragmentSectionBinding
import ru.slatinin.nytnews.utils.ConnectionUtil
import ru.slatinin.nytnews.viewmodels.SectionViewModel

@AndroidEntryPoint
class SectionFragment : Fragment() {
    private var _binding: FragmentSectionBinding? = null

    private val binding get() = _binding!!

    val viewModel: SectionViewModel by viewModels()

    private var nytJob: Job? = null
    private var nytAdapter: NytPopularAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)
        binding.sectionToolbar.title = viewModel.section
        binding.sectionToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.sectionNytList.layoutManager = getLayoutManager()
        if (nytAdapter == null){
            nytAdapter = NytPopularAdapter(object : BrowseLinkCallback {
                override fun onLoadLink(url: String) {
                    findNavController().navigate(SectionFragmentDirections.sectionToBrowser(url))
                }
            })
        }
        binding.sectionNytList.adapter = nytAdapter
        lifecycleScope.launch {
            nytAdapter?.let {
                it.loadStateFlow.collectLatest { loadStates ->
                    binding.sectionProgress.isVisible = loadStates.refresh is LoadState.Loading
                    binding.sectionProgress.isVisible =
                        loadStates.refresh is LoadState.NotLoading && loadStates.refresh !is LoadState.Error
                    binding.sectionProgress.isVisible = loadStates.refresh is LoadState.Error
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
            binding.sectionError.isVisible = ConnectionUtil.isOnline(it)
            if (ConnectionUtil.isOnline(it)) {
                search()
            }
        }
        return binding.root
    }

    private fun search() {

        nytJob?.cancel()
        nytJob = lifecycleScope.launch {
            viewModel.loadSection().collectLatest {
                nytAdapter?.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
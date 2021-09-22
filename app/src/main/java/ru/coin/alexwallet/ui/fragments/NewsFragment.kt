package ru.coin.alexwallet.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.coin.alexwallet.adapters.NewsAdapter
import ru.coin.alexwallet.databinding.FragmentNewsBinding
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
        var lm: LinearLayoutManager
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
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
        binding.recommendedListNews.layoutManager = lm
        binding.recommendedListNews.adapter = newsAdapter
        search()
        return binding.root
    }

    private fun search() {
        // Make sure we cancel the previous job before creating a new one
        recommendedNewsJob?.cancel()
        recommendedNewsJob = lifecycleScope.launch {
            viewModel.searchPictures()?.collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }

}
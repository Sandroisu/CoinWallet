package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.slatinin.nytnews.databinding.FragmentSectionBinding
import ru.slatinin.nytnews.viewmodels.SectionViewModel

class SectionFragment : Fragment() {
    private var _binding: FragmentSectionBinding? = null

    private val binding get() = _binding!!

    val viewModel: SectionViewModel by viewModels()

    private var nytJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)
        binding.sectionToolbar.title = viewModel.section
        binding.sectionToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        return binding.root
    }

    private fun search() {

        nytJob?.cancel()
        nytJob = lifecycleScope.launch {
            viewModel.loadSection().collectLatest {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
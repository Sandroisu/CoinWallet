package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.slatinin.nytnews.databinding.FragmentSectionBinding
import ru.slatinin.nytnews.viewmodels.SectionViewModel
import javax.inject.Inject

class SectionFragment : Fragment() {
    private var _binding: FragmentSectionBinding? = null

    private val binding get() = _binding!!

    val viewModel: SectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)
        binding.sectionToolbar.title = viewModel.section
        binding.sectionToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
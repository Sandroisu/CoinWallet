package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.adapters.SectionClickListener
import ru.slatinin.nytnews.adapters.SectionsAdapter
import ru.slatinin.nytnews.data.models.SectionItem
import ru.slatinin.nytnews.databinding.FragmentSectionBinding
import ru.slatinin.nytnews.databinding.FragmentSectionsBinding
import ru.slatinin.nytnews.viewmodels.SectionViewModel
import ru.slatinin.nytnews.viewmodels.SectionsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SectionsFragment : Fragment() {
    private var _binding: FragmentSectionsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SectionsViewModel by viewModels {
        factory
    }

    @Inject
    lateinit var factory: SectionsViewModel.SectionsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionsBinding.inflate(inflater, container, false)
        binding.sectionsList.layoutManager = LinearLayoutManager(requireContext())
        val sectionClickListener = object : SectionClickListener {
            override fun onSectionClick(sectionItem: SectionItem) {
                val action = SectionsFragmentDirections.sectionsToSection(sectionItem.sectionName)
                findNavController().navigate(action)
            }
        }
        val sectionsAdapter = SectionsAdapter(viewModel.sections, sectionClickListener)
        binding.sectionsList.adapter = sectionsAdapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
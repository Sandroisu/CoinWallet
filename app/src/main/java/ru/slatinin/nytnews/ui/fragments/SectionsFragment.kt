package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.databinding.FragmentSectionsBinding
import ru.slatinin.nytnews.viewmodels.SectionsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SectionsFragment : Fragment() {

    private var _binding: FragmentSectionsBinding? = null

    private val binding get() = _binding!!

    private  val viewModel: SectionsViewModel by viewModels{
        factory
    }
    @Inject
    lateinit var factory: SectionsViewModel.SectionsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionsBinding.inflate(inflater, container, false)
        for (sec in viewModel.sections){
            Toast.makeText(context, sec.sectionName, Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
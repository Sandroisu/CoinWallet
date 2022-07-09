package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.slatinin.nytnews.databinding.FragmentSearchBinding
import ru.slatinin.nytnews.viewmodels.WalletViewModel

class SearchFragment : Fragment() {

    private val viewModel: WalletViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }



}
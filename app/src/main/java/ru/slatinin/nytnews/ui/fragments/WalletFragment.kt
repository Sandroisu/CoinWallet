package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.slatinin.nytnews.databinding.FragmentWalletBinding
import ru.slatinin.nytnews.viewmodels.WalletViewModel

class WalletFragment : Fragment() {

    private val viewModel: WalletViewModel by viewModels()
    private lateinit var binding: FragmentWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }



}
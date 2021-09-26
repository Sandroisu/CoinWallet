package ru.coin.alexwallet.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.coin.alexwallet.databinding.FragmentBrowserBinding
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import ru.coin.alexwallet.R


class BrowserFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBrowserBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

}
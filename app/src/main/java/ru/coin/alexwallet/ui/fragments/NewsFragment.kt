package ru.coin.alexwallet.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.coin.alexwallet.R
import ru.coin.alexwallet.adapters.CurrencyAdapter
import ru.coin.alexwallet.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

 private val currencyAdapter = CurrencyAdapter()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        val coinsRecyclerView = binding.currencyListNews
        coinsRecyclerView.adapter = currencyAdapter
        return binding.root
    }


}
package ru.coin.alexwallet.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.coin.alexwallet.databinding.FragmentBrowserFavoritesBinding

class BrowserFavorites : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBrowserFavoritesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

}
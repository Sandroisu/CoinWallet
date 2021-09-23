package ru.coin.alexwallet.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.coin.alexwallet.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener { findNavController().navigate(R.id.action_fragment_splash_to_account_fragment) }
        view.postDelayed(
            { findNavController().navigate(R.id.action_fragment_splash_to_account_fragment) },
            700
        )
    }
}
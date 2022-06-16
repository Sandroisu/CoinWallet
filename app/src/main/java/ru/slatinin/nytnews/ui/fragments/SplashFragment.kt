package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.slatinin.nytnews.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.postDelayed(
            {
            },
            500
        )
    }
}
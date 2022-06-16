package ru.slatinin.nytnews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.adapters.*
import ru.slatinin.nytnews.databinding.FragmentViewPagerBinding

class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val viewPager = binding.viewPager
        viewPager.adapter = MainViewPagerAdapter(this)
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
        }.attach()
        viewPager.isUserInputEnabled = false
        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            NEWS_INDEX -> R.drawable.ic_news
            BROWSER_INDEX -> R.drawable.ic_browser
            WALLET_INDEX -> R.drawable.ic_wallet
            EXCHANGE_INDEX -> R.drawable.ic_exchange
            SETTINGS_INDEX -> R.drawable.ic_settings
            else -> throw IndexOutOfBoundsException()
        }
    }

}

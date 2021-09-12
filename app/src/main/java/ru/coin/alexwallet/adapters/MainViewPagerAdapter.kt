package ru.coin.alexwallet.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.coin.alexwallet.ui.fragments.*

const val NEWS_INDEX = 0
const val BROWSER_INDEX = 1
const val WALLET_INDEX = 2
const val EXCHANGE_INDEX = 3
const val SETTINGS_INDEX = 4

class MainViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    private val bottomNavFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        NEWS_INDEX to { NewsFragment() },
        BROWSER_INDEX to { BrowserFragment() },
        WALLET_INDEX to { WalletFragment() },
        EXCHANGE_INDEX to { ExchangeFragment() },
        SETTINGS_INDEX to { SettingsFragment() }
    )

    override fun getItemCount(): Int {
        return bottomNavFragmentsCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return bottomNavFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
package ru.coin.alexwallet.ui

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import ru.coin.alexwallet.R
import ru.coin.alexwallet.databinding.MainActivityBinding
import ru.coin.alexwallet.storage.AppDatabase

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var needTranslationReanimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CoinWallet)
        super.onCreate(savedInstanceState)
        setContentView<MainActivityBinding>(this, R.layout.main_activity)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.news_fragment,
                R.id.browser_fragment,
                R.id.wallet_fragment,
                R.id.exchange_fragment,
                R.id.settings_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.hide()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.browser_history_fragment, R.id.browser_favorites_fragment -> bottomNavigationView.isVisible = false
                R.id.browser_fragment -> {
                    closeWithTranslationAnimation(bottomNavigationView)
                }
                else -> {
                    if (needTranslationReanimation) {
                        openWithTranslationAnimation(bottomNavigationView)
                    }
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun closeWithTranslationAnimation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.animate()
            .translationY(bottomNavigationView.height.toFloat()).setListener(object :
                Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationView.clearAnimation()
                    bottomNavigationView.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
            .duration = 300
        needTranslationReanimation = true
    }

    private fun openWithTranslationAnimation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.animate()
            .translationY(0F).setListener(object :
                Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationView.clearAnimation()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            })
            .duration = 300
        needTranslationReanimation = false
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}
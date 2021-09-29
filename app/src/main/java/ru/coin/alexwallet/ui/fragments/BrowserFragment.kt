package ru.coin.alexwallet.ui.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.coin.alexwallet.R
import ru.coin.alexwallet.databinding.FragmentBrowserBinding
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat


import ru.coin.alexwallet.component.CoinEditText
import ru.coin.alexwallet.component.CoinEditTextImeBackListener
import ru.coin.alexwallet.component.OnEndIconClickListener
import androidx.core.content.ContextCompat.getSystemService


class BrowserFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.fragmentBrowserWebView.webViewClient = MyWebViewClient()
        binding.fragmentBrowserEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var text = binding.fragmentBrowserEditText.text.toString()
                if (text.isEmpty()) {
                    return@setOnEditorActionListener false
                }
                if (!text.startsWith("https://") && !text.startsWith("http://")) {
                    text = "https://$text"
                }
                text = text.filterNot { it.isWhitespace() }
                binding.fragmentBrowserEditText.setText(text)
                binding.fragmentBrowserWebView.loadUrl(text)
                binding.fragmentBrowserImage.isVisible = false
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }
        binding.fragmentBrowserEditText.setOnCloseIconListener(object : OnEndIconClickListener {
            override fun onClose() {
                restoreStartScreen()
                if (activity?.currentFocus != null) {
                    val imm: InputMethodManager? =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
                }
            }

        })
        binding.fragmentBrowserEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.fragmentBrowserRootLayout)
                constraintSet.clear(binding.fragmentBrowserEditText.id, ConstraintSet.BOTTOM)
                constraintSet.clear(binding.fragmentBrowserWebView.id, ConstraintSet.TOP)
                constraintSet.connect(
                    binding.fragmentBrowserEditText.id,
                    ConstraintSet.TOP,
                    binding.fragmentBrowserRootLayout.id,
                    ConstraintSet.TOP
                )
                constraintSet.connect(
                    binding.fragmentBrowserWebView.id,
                    ConstraintSet.TOP,
                    binding.fragmentBrowserEditText.id,
                    ConstraintSet.BOTTOM
                )
                constraintSet.applyTo(binding.fragmentBrowserRootLayout)
            }
        }
        binding.setClickListener {
            findNavController().navigate(R.id.action_browser_to_browser_history)
        }

        binding.fragmentBrowserEditText.setOnImeBackListener(object :
            CoinEditTextImeBackListener {
            override fun onImeBack(ctrl: CoinEditText, text: String) {
                if (text.isEmpty()) {
                    restoreStartScreen()
                }
            }
        })
        binding.fragmentBrowserWebView.setBackgroundColor(Color.TRANSPARENT)
        return binding.root
    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        //необходимо чтобы вызвать onCreateAnimator
        return null
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        //необходим для кастомной анимации startDestination фрагмента в navigation
        return if (enter) {
            AnimatorInflater.loadAnimator(activity, ru.coin.alexwallet.R.animator.slide_in_bottom)
        } else {
            AnimatorInflater.loadAnimator(activity, R.animator.slide_out_bottom)
        }
    }

    private class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    private fun restoreStartScreen() {
        val constraintSet = ConstraintSet()
        binding.fragmentBrowserWebView.loadUrl("about:blank")
        constraintSet.clone(binding.fragmentBrowserRootLayout)
        constraintSet.clear(binding.fragmentBrowserEditText.id, ConstraintSet.TOP)
        constraintSet.clear(binding.fragmentBrowserWebView.id, ConstraintSet.TOP)
        constraintSet.connect(
            binding.fragmentBrowserEditText.id,
            ConstraintSet.BOTTOM,
            binding.fragmentBrowserGuide30h.id,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            binding.fragmentBrowserWebView.id,
            ConstraintSet.TOP,
            binding.fragmentBrowserGuide30h.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(binding.fragmentBrowserRootLayout)
        binding.fragmentBrowserEditText.clearFocus()
    }

}
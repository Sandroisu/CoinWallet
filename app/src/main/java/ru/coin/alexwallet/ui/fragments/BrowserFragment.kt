package ru.coin.alexwallet.ui.fragments

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.coin.alexwallet.R
import ru.coin.alexwallet.databinding.FragmentBrowserBinding


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
        binding.fragmentBrowserWebView.webViewClient = MyWebViewClient()
        binding.fragmentBrowserEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var text = binding.fragmentBrowserEditText.text.toString()
                if (!text.startsWith("https://")) {
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
        binding.setClickListener {
            findNavController().navigate(R.id.action_browser_to_browser_history)
        }
        binding.fragmentBrowserWebView.setBackgroundColor(Color.TRANSPARENT)
        return binding.root
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {

        return null
    }

    @SuppressLint("Recycle")
    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        var animator = ObjectAnimator.ofFloat(view, "translationY", 0f, 100f)

        return animator
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

}
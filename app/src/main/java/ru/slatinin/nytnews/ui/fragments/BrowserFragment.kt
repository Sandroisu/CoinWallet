package ru.slatinin.nytnews.ui.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.slatinin.nytnews.R
import ru.slatinin.nytnews.databinding.FragmentBrowserBinding
import androidx.constraintlayout.widget.ConstraintSet

import ru.slatinin.nytnews.component.NYTEditText
import ru.slatinin.nytnews.component.CoinEditTextImeBackListener
import ru.slatinin.nytnews.component.OnEndIconClickListener
import androidx.activity.OnBackPressedCallback

const val ABOUT_BLANK = "about:blank"

class BrowserFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentBrowserBinding
    private var anythingToHandleBeforeNavigateUp = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.fragmentBrowserWebView.webViewClient =
            MyWebViewClient(binding.fragmentBrowserEditText)
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
                anythingToHandleBeforeNavigateUp = true
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
                binding.fragmentBrowserEditText.clearFocus()
            }

        })
        binding.fragmentBrowserEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.fragmentBrowserFavoritesCard.visibility = View.GONE
                binding.fragmentBrowserHistoryCard.visibility = View.GONE
                binding.fragmentBrowserClose.visibility = View.GONE
                binding.fragmentBrowserTitle.visibility = View.GONE
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.fragmentBrowserRootLayout)
                constraintSet.clear(binding.fragmentBrowserEditText.id, ConstraintSet.BOTTOM)
                constraintSet.clear(binding.fragmentBrowserWebView.id, ConstraintSet.TOP)
                constraintSet.connect(
                    binding.fragmentBrowserEditText.id,
                    ConstraintSet.TOP,
                    binding.fragmentBrowserRootLayout.id,
                    ConstraintSet.TOP, resources.getDimensionPixelSize(R.dimen.standard_margin)
                )
                constraintSet.connect(
                    binding.fragmentBrowserWebView.id,
                    ConstraintSet.TOP,
                    binding.fragmentBrowserEditText.id,
                    ConstraintSet.BOTTOM, resources.getDimensionPixelSize(R.dimen.standard_margin)
                )
                constraintSet.applyTo(binding.fragmentBrowserRootLayout)
                v.postDelayed({
                    if (!v.hasFocus()) {
                        v.requestFocus()
                    }
                }, 200)
            }
        }
        binding.setClickListener {
            when (it.id) {
                binding.fragmentBrowserFavoritesCard.id -> findNavController().navigate(R.id.action_browser_to_browser_favorites)
                binding.fragmentBrowserHistoryCard.id -> findNavController().navigate(R.id.action_browser_to_browser_history)
            }
        }

        binding.fragmentBrowserEditText.setOnImeBackListener(object :
            CoinEditTextImeBackListener {
            override fun onImeBack(ctrl: NYTEditText, text: String) {
                if (text.isEmpty()) {
                    restoreStartScreen()
                    binding.fragmentBrowserEditText.clearFocus()
                }
            }
        })
        binding.fragmentBrowserWebView.setBackgroundColor(Color.TRANSPARENT)
        binding.fragmentBrowserWebView.settings.javaScriptEnabled = true
        binding.fragmentBrowserClose.setOnClickListener { findNavController().navigateUp() }
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val backForwardList = binding.fragmentBrowserWebView.copyBackForwardList()
                    val index = backForwardList.currentIndex
                    if (binding.fragmentBrowserWebView.canGoBack() && index > 0) {
                        binding.fragmentBrowserWebView.goBack()
                        binding.fragmentBrowserEditText.text = Editable.Factory.getInstance()
                            .newEditable(
                                backForwardList.getItemAtIndex(index - 1).originalUrl
                            )
                    } else {
                        if (anythingToHandleBeforeNavigateUp) {
                            binding.fragmentBrowserEditText.text =
                                Editable.Factory.getInstance().newEditable("")
                            restoreStartScreen()
                        } else {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        arguments?.let {
            val url = BrowserFragmentArgs.fromBundle(it).url
            url?.let {
                binding.fragmentBrowserWebView.loadUrl(url)
            }
        }
        return binding.root

    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        //необходимо чтобы вызвать onCreateAnimator
        return null
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        //необходим для кастомной анимации startDestination фрагмента в navigation
        return if (enter) {
            AnimatorInflater.loadAnimator(activity, R.animator.slide_in_bottom)
        } else {
            AnimatorInflater.loadAnimator(activity, R.animator.slide_out_bottom)
        }
    }

    private class MyWebViewClient(val editText: NYTEditText) : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            if (request.url.toString().contains("http")) {
                editText.text = Editable.Factory.getInstance().newEditable(request.url.toString())
                view.loadUrl(request.url.toString())
                view.setBackgroundColor(Color.WHITE)
            }
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            if (ABOUT_BLANK == url) {
                view?.clearHistory()
            } else {
                super.onPageFinished(view, url)
            }
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            view?.setBackgroundColor(Color.WHITE)

        }


    }

    private fun restoreStartScreen() {
        val constraintSet = ConstraintSet()
        binding.fragmentBrowserWebView.loadUrl(ABOUT_BLANK)
        binding.fragmentBrowserWebView.setBackgroundColor(Color.TRANSPARENT)
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
        binding.fragmentBrowserClose.visibility = View.VISIBLE
        binding.fragmentBrowserTitle.visibility = View.VISIBLE
        binding.fragmentBrowserHistoryCard.visibility = View.VISIBLE
        binding.fragmentBrowserFavoritesCard.visibility = View.VISIBLE
        anythingToHandleBeforeNavigateUp = false
    }

}
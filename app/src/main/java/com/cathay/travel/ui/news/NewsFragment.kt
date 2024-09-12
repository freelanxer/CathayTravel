package com.cathay.travel.ui.news

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.cathay.travel.databinding.FragmentNewsBinding
import com.cathay.travel.extension.onBackButtonPressed
import com.cathay.travel.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebViewConfig()
        onBackButtonPressed {
            val canGoBack = binding.newsWebView.canGoBack()
            binding.newsWebView.goBack()
            canGoBack
        }
    }

    override fun onResume() {
        super.onResume()
        val news = viewModel.selectedNewsLiveData.value
        news?.url?.let {
            binding.newsWebView.loadUrl(it)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewConfig() {
        binding.newsWebView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    _binding?.loadingView?.visibility = View.VISIBLE
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    _binding?.loadingView?.visibility = View.GONE
                }
            }
            webChromeClient = object : WebChromeClient() {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
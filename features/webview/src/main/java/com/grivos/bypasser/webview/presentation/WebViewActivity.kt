package com.grivos.bypasser.webview.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.*
import androidx.core.view.isVisible
import com.grivos.bypasser.webview.R
import com.grivos.navigation.features.WebViewNavigation
import com.grivos.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebViewActivity : BaseActivity(), WebViewView {

    private val url by lazy { intent.getStringExtra(WebViewNavigation.KEY_URL) }
    private val presenter: WebViewPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setupWebView()
        setupToolbar()
        presenter.url = url
        presenter.attachView(this, lifecycle)
    }

    private fun setupToolbar() {
        webViewToolbar.inflateMenu(R.menu.webview)
        webViewToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_share_url -> {
                    presenter.onClickShareUrl()
                    true
                }
                else -> false
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webViewWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                webViewWebView.loadUrl(request.url.toString())
                return true
            }
        }
        webViewWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                webViewProgress.progress = newProgress
                webViewProgress.isVisible = newProgress != MAX_PROGRESS
            }
        }

        val webSettings = webViewWebView.settings
        webSettings.javaScriptEnabled = true
        CookieManager.getInstance().setAcceptCookie(false)
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setAppCacheEnabled(false)
        webViewWebView.clearHistory()
        webViewWebView.clearCache(true)
    }

    override fun loadUrl(url: String) {
        webViewWebView.loadUrl(url)
    }

    override fun shareUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_url)))
    }

    companion object {
        private const val MAX_PROGRESS = 100
    }
}

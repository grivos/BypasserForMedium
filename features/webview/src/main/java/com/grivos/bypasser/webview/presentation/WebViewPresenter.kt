package com.grivos.bypasser.webview.presentation

import androidx.lifecycle.Lifecycle
import com.grivos.presentation.BasePresenter
import com.grivos.presentation.MvpView

interface WebViewView : MvpView {
    fun loadUrl(url: String)
    fun shareUrl(url: String)
}

class WebViewPresenter : BasePresenter<WebViewView>() {

    lateinit var url: String

    override fun attachView(mvpView: WebViewView, viewLifecycle: Lifecycle) {
        super.attachView(mvpView, viewLifecycle)
        mvpView.loadUrl(url)
    }

    fun onClickShareUrl() {
        mvpView?.shareUrl(url)
    }
}

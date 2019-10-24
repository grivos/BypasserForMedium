package com.grivos.navigation.features

import android.content.Intent
import com.grivos.navigation.loadIntentOrNull

object WebViewNavigation {

    const val KEY_URL = "url"
    private const val WEB_VIEW_PACKAGE = "com.grivos.bypasser.webview.presentation.WebViewActivity"

    fun webViewForUrl(url: String): Intent? =
        WEB_VIEW_PACKAGE.loadIntentOrNull()
            ?.apply {
                putExtra(KEY_URL, url)
            }
}

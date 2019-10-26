package com.grivos.bypasser.intercept.presentation

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.grivos.bypasser.intercept.R
import com.grivos.navigation.features.WebViewNavigation
import com.grivos.presentation.BaseActivity
import com.grivos.presentation.makeStatusBarTransparent
import kotlinx.android.synthetic.main.activity_intercept.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InterceptActivity : BaseActivity(), InterceptView {

    private val presenter: InterceptPresenter by viewModel()
    private val url by lazy {
        val raw = intent.dataString ?: throw IllegalArgumentException("Uri can't be null")
        val uri = Uri.parse(raw)
        if (uri.host.contains("medium.com")) {
            raw
        } else {
            // We add the 'medium.com' host to custom domain uris to avoid infinite loops
            // when opening the link with the Medium app
            "https://medium.com/${uri.host}${uri.path}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        setContentView(R.layout.activity_intercept)
        presenter.url = url
        presenter.attachView(this, lifecycle)
    }

    override fun toggleProgress(visible: Boolean) {
        interceptProgress.isVisible = visible
    }

    override fun showToast(textResId: Int) {
        Toast.makeText(this, textResId, Toast.LENGTH_SHORT).show()
    }

    override fun openExternally(packageName: String, activityName: String, url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            component = ComponentName(packageName, activityName)
        }
        startActivity(intent)
    }

    override fun openInternally(url: String) {
        WebViewNavigation.webViewForUrl(url)?.let { startActivity(it) }
    }
}

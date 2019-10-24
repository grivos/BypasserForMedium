package com.grivos.bypasser.webview.inject

import com.grivos.bypasser.webview.presentation.WebViewPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object WebViewModules {
    fun injectFeature() = loadFeature
}

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel { WebViewPresenter() }
}

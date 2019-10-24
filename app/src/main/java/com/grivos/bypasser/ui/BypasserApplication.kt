package com.grivos.bypasser.ui

import android.app.Application
import com.grivos.bypasser.BuildConfig
import com.grivos.bypasser.intercept.inject.InterceptModules
import com.grivos.bypasser.main.inject.MainModules
import com.grivos.bypasser.webview.inject.WebViewModules
import com.grivos.cache.CacheLibrary
import com.grivos.fallback.inject.FallbackModules
import com.grivos.spanomatic.SpanomaticInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BypasserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initKoin()
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    SpanomaticInterceptor()
                )
                .build()
        )
        CacheLibrary.init(this)
    }

    private fun initKoin() {
        startKoin { androidContext(this@BypasserApplication) }
        InterceptModules.injectFeature()
        FallbackModules.injectFeature()
        MainModules.injectFeature()
        WebViewModules.injectFeature()
    }
}

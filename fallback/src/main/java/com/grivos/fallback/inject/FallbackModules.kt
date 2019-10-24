package com.grivos.fallback.inject

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.grivos.fallback.MediumFallbackHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object FallbackModules {
    fun injectFeature() = loadFeature
}

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            fallbackModule
        )
    )
}

val fallbackModule: Module = module {
    single {
        val preferences =
            androidContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        RxSharedPreferences.create(preferences)
    }
    single { MediumFallbackHandler(context = androidContext(), rxSharedPreferences = get()) }
}

private const val PREFERENCE_NAME = "fallback"

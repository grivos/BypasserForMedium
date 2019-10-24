package com.grivos.bypasser.main.inject

import com.grivos.bypasser.main.domain.usecase.MainUseCase
import com.grivos.bypasser.main.presentation.MainPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object MainModules {
    fun injectFeature() = loadFeature
}

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            useCaseModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel { MainPresenter(mainUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { MainUseCase(mediumFallbackHandler = get()) }
}

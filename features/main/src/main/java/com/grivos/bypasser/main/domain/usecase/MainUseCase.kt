package com.grivos.bypasser.main.domain.usecase

import com.grivos.fallback.MediumFallbackHandler
import com.grivos.fallback.domain.model.InstalledAppInfo
import com.grivos.fallback.domain.model.MediumFallbackApp
import io.reactivex.Observable
import io.reactivex.Single

class MainUseCase(
    private val mediumFallbackHandler: MediumFallbackHandler
) {

    fun getFallbackApp(): Observable<MediumFallbackApp> =
        mediumFallbackHandler.getFallbackApp()

    fun setFallbackApp(packageName: String) {
        mediumFallbackHandler.setFallbackApp(packageName)
    }

    fun getPossibleFallbackApps(): Single<List<InstalledAppInfo>> =
        mediumFallbackHandler.getPossibleFallbackApps()
}

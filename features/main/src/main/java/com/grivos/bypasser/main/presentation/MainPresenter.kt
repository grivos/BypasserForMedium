package com.grivos.bypasser.main.presentation

import androidx.lifecycle.Lifecycle
import com.grivos.bypasser.main.domain.usecase.MainUseCase
import com.grivos.fallback.domain.model.InstalledAppInfo
import com.grivos.fallback.domain.model.MediumFallbackApp
import com.grivos.presentation.BasePresenter
import com.grivos.presentation.MvpView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

interface MainView : MvpView {
    fun showFallbackApp(fallbackApp: MediumFallbackApp)
    fun showChooseFallbackDialog(apps: List<InstalledAppInfo>)
    fun animateLogo()
}

class MainPresenter(
    private val mainUseCase: MainUseCase
) : BasePresenter<MainView>() {

    override fun attachView(mvpView: MainView, viewLifecycle: Lifecycle) {
        super.attachView(mvpView, viewLifecycle)
        launch {
            mainUseCase.getFallbackApp()
                .subscribe {
                    this.mvpView?.showFallbackApp(it)
                }
        }
        launch {
            Completable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { this.mvpView?.animateLogo() }
        }
    }

    fun onClickChangeFallbackApp() {
        launch {
            mainUseCase
                .getPossibleFallbackApps()
                .subscribe { list ->
                    mvpView?.showChooseFallbackDialog(list)
                }
        }
    }

    fun onClickedApp(appInfo: InstalledAppInfo) {
        mainUseCase.setFallbackApp(appInfo.packageName)
    }
}

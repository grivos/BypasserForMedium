package com.grivos.bypasser.intercept.presentation

import androidx.lifecycle.Lifecycle
import com.grivos.bypasser.intercept.R
import com.grivos.bypasser.intercept.domain.usecase.MediumArticlesInterceptUseCase
import com.grivos.fallback.domain.model.MediumFallbackAppExists
import com.grivos.fallback.domain.model.MediumFallbackAppNone
import com.grivos.fallback.domain.model.MediumInterceptionResultNotPremium
import com.grivos.fallback.domain.model.MediumInterceptionResultPremium
import com.grivos.presentation.BasePresenter
import com.grivos.presentation.MvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

interface InterceptView : MvpView {
    fun toggleProgress(visible: Boolean)
    fun showToast(textResId: Int)
    fun openExternally(packageName: String, activityName: String, url: String)
    fun openInternally(url: String)
    fun finish()
}

class InterceptPresenter(
    private val mediumArticlesInterceptUseCase: MediumArticlesInterceptUseCase
) : BasePresenter<InterceptView>() {

    lateinit var url: String

    override fun attachView(mvpView: InterceptView, viewLifecycle: Lifecycle) {
        super.attachView(mvpView, viewLifecycle)
        mvpView.toggleProgress(true)
        launch {
            mediumArticlesInterceptUseCase
                .getInterceptResult(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ interceptResult ->
                    when (interceptResult) {
                        MediumInterceptionResultPremium -> {
                            this.mvpView?.showToast(R.string.premium_article_detected)
                            this.mvpView?.openInternally(url)
                        }
                        is MediumInterceptionResultNotPremium -> {
                            when (val fallbackApp = interceptResult.fallbackApp) {
                                MediumFallbackAppNone -> {
                                    this.mvpView?.showToast(R.string.non_premium_article_detected)
                                    this.mvpView?.openInternally(url)
                                }
                                is MediumFallbackAppExists -> {
                                    this.mvpView?.showToast(R.string.non_premium_article_detected)
                                    this.mvpView?.openExternally(
                                        fallbackApp.app.packageName,
                                        fallbackApp.app.activityName,
                                        url
                                    )
                                }
                            }
                        }
                    }
                    this.mvpView?.toggleProgress(false)
                    this.mvpView?.finish()
                }, { error ->
                    Timber.e(error, "error loading page")
                    this.mvpView?.toggleProgress(false)
                    this.mvpView?.showToast(R.string.error_loading_page)
                    this.mvpView?.finish()
                })
        }
    }
}

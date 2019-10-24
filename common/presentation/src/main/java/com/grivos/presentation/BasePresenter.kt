package com.grivos.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpView> : ViewModel(), LifecycleObserver {

    protected var mvpView: T? = null
        private set
    private var viewLifecycle: Lifecycle? = null
    private val disposables = CompositeDisposable()

    @CallSuper
    open fun attachView(mvpView: T, viewLifecycle: Lifecycle) {
        this.mvpView = mvpView
        this.viewLifecycle = viewLifecycle
        viewLifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onViewDestroyed() {
        disposables.clear()
        mvpView = null
        viewLifecycle = null
    }

    protected fun launch(action: () -> Disposable) {
        disposables.add(action())
    }

    protected val isViewAttached: Boolean
        get() = mvpView != null
}

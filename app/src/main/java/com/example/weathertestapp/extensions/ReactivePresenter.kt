package com.example.weathertestapp.extensions

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class ReactivePresenter<V : MvpView> : MvpPresenter<V>() {

    private val globalDisposable = CompositeDisposable()

    override fun onDestroy() {
        globalDisposable.dispose()
        super.onDestroy()
    }

    protected fun Disposable.untilDestroy() {
        globalDisposable.add(this)
    }

    protected fun <T> Single<T>.asyncQuery(
        subscribeObservable: Scheduler = Schedulers.io(),
        observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    ): Single<T> {
        return this
            .subscribeOn(subscribeObservable)
            .observeOn(observeScheduler)
    }

    protected fun <T> Single<T>.bindProgress(onProgress: (inProgress: Boolean) -> Unit): Single<T> {
        return this
            .doOnSubscribe { onProgress.invoke(true) }
            .doOnSuccess { onProgress.invoke(false) }
            .doOnError { onProgress.invoke(false) }
    }
}

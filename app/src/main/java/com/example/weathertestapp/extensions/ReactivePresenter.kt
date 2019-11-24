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

    protected fun <T> Observable<T>.asyncQuery(
        subscribeObservable: Scheduler = Schedulers.io(),
        observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    ): Observable<T> {
        return this
            .subscribeOn(subscribeObservable)
            .observeOn(observeScheduler)
    }

    protected fun <T> Single<T>.asyncQuery(
        subscribeObservable: Scheduler = Schedulers.io(),
        observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    ): Single<T> {
        return this
            .subscribeOn(subscribeObservable)
            .observeOn(observeScheduler)
    }

    protected fun Completable.asyncQuery(
        subscribeObservable: Scheduler = Schedulers.io(),
        observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    ): Completable {
        return this
            .subscribeOn(subscribeObservable)
            .observeOn(observeScheduler)
    }

    protected fun <T> Observable<T>.bindProgress(onProgress: (inProgress: Boolean) -> Unit): Observable<T> {
        return this
            .doOnSubscribe { onProgress.invoke(true) }
            .doOnNext { onProgress.invoke(false) }
            .doOnError { onProgress.invoke(false) }
    }

    protected fun <T> Single<T>.bindProgress(onProgress: (inProgress: Boolean) -> Unit): Single<T> {
        return this
            .doOnSubscribe { onProgress.invoke(true) }
            .doOnSuccess { onProgress.invoke(false) }
            .doOnError { onProgress.invoke(false) }
    }

    protected fun Completable.bindProgress(onProgress: (inProgress: Boolean) -> Unit): Completable {
        return this
            .doOnSubscribe { onProgress.invoke(true) }
            .doOnComplete { onProgress.invoke(false) }
            .doOnError { onProgress.invoke(false) }
    }
}
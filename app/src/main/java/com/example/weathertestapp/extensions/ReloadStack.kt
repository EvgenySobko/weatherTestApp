package com.example.weathertestapp.extensions

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single

object ReloadStack {

    private val errorStack = mutableMapOf<Int, ReloadItem>()

    fun <T> addToReloadStack(singleSource: Single<T>, reloadKey: Int? = null): Observable<T> {

        val key: Int = reloadKey ?: singleSource.hashCode()
        val retrySubject = BehaviorRelay.createDefault(Unit).toSerialized()
        val itemAndSource = ReloadItem(retrySubject, true)
        errorStack[key] = itemAndSource

        return itemAndSource.source
            .filter { itemAndSource.needToReload }
            .flatMap { singleSource.toObservable() }
            .doOnError { errorStack[key]!!.needToReload = false }
            .doOnNext { errorStack.remove(key) }
    }

    fun reloadAll() {
        errorStack.forEach { (_, source) ->
            source.needToReload = true
            source.source.accept(Unit)
        }
    }
}

private class ReloadItem(
    var source: Relay<Unit>,
    var needToReload: Boolean
)

fun <T> Single<T>.globalReload(): Observable<T> =
    ReloadStack.addToReloadStack(this)
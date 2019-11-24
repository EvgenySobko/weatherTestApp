package com.example.weathertestapp.ui.forecast.mvp

import android.location.Location
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.weathertestapp.extensions.globalReload
import com.example.weathertestapp.extensions.ReactivePresenter
import com.example.weathertestapp.gateway.WeatherGateway

@InjectViewState
class WeatherPresenter: ReactivePresenter<WeatherMvpView>() {

    fun loadCurrentData(position: Location) {
        viewState.inProgress(true)
        WeatherGateway.getCurrentWeather(position.latitude, position.longitude)
            .asyncQuery()
            .globalReload()
            .doOnNext {
                viewState.currentDataLoaded(it)
                Log.d("LOADED", it.toString())
                return@doOnNext
            }
            .doOnError {
                Log.d("ERROR", it.message.toString())
            }
            .retry()
            .subscribe()
            .untilDestroy()
    }

    fun loadForecastData(position: Location) {
        WeatherGateway.getFiveDaysWeather(position.latitude, position.longitude)
            .asyncQuery()
            .globalReload()
            .doOnNext {
                viewState.forecastDataLoaded(it)
                viewState.inProgress(false)
                Log.d("LOADED", it.toString())
                return@doOnNext
            }
            .doOnError {
                Log.d("ERROR", it.message.toString())
            }
            .retry()
            .subscribe()
            .untilDestroy()
    }
}
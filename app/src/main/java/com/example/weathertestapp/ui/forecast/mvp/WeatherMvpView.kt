package com.example.weathertestapp.ui.forecast.mvp

import com.arellomobile.mvp.MvpView
import com.example.weathertestapp.domain.ForecastResponse
import com.example.weathertestapp.domain.Response

interface WeatherMvpView: MvpView {

    fun currentDataLoaded(data: Response)

    fun forecastDataLoaded(data: ForecastResponse)

    fun inProgress(show: Boolean)
}
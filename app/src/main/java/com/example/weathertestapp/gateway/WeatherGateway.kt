package com.example.weathertestapp.gateway

import com.example.weathertestapp.api.ApiFactory
import com.example.weathertestapp.api.ApiService

object WeatherGateway {

    private val api: ApiService = ApiFactory.service

    fun getCurrentWeather(latitude: Double, longitude: Double) =
        api.getCurrentWeather(latitude = latitude, longitude = longitude)

    fun getFiveDaysWeather(latitude: Double, longitude: Double) =
        api.getForecastWeather(latitude = latitude, longitude = longitude)
}
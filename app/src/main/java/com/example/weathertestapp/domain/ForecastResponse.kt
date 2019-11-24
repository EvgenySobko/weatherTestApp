package com.example.weathertestapp.domain

data class ForecastResponse(
    val cod: Int,
    val message: String,
    val cnt: Int,
    val list: List<Response>,
    val city: City
)
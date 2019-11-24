package com.example.weathertestapp.api

import com.example.weathertestapp.domain.ForecastResponse
import com.example.weathertestapp.domain.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather?")
    fun getCurrentWeather(
        @Query("lat") latitude: Double? = null,
        @Query("lon") longitude: Double? = null,
        @Query("APPID") appid: String = "479f4c3a4051df749007df0c10a7a1b6"
    ): Single<Response>

    @GET("forecast?")
    fun getForecastWeather(
        @Query("lat") latitude: Double? = null,
        @Query("lon") longitude: Double? = null,
        @Query("APPID") appid: String = "479f4c3a4051df749007df0c10a7a1b6"
    ): Single<ForecastResponse>
}
package com.example.weathertestapp.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    val service: ApiService = getBuilder().create(ApiService::class.java)

    private fun getBuilder(): Retrofit = Retrofit.Builder()
            .client(buildClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(createGsonBuilder()))
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()

    private fun buildClient() = OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    private fun createGsonBuilder() =
            GsonBuilder().apply {
                registerTypeAdapter(DateTime::class.java, DateTimeTypeConverter())
            }.setLenient().create()
}
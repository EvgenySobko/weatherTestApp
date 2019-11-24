package com.example.weathertestapp.ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.weathertestapp.R
import com.example.weathertestapp.domain.ForecastResponse
import com.example.weathertestapp.domain.Response
import com.example.weathertestapp.extensions.GlideLoaderUtils
import com.example.weathertestapp.ui.forecast.mvp.WeatherMvpView
import com.example.weathertestapp.ui.forecast.mvp.WeatherPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment: MvpAppCompatFragment(), WeatherMvpView {

    @InjectPresenter
    lateinit var presenter: WeatherPresenter

    private lateinit var fusedLocationListener: FusedLocationProviderClient

    private val forecastAdapter = ForecastAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        fusedLocationListener = LocationServices.getFusedLocationProviderClient(activity!!)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
        onInitView()
    }

    private fun onInitView() {
        with(forecast) {
            scrollToPosition(0)
            adapter = forecastAdapter
        }
    }

    private fun getLocation() {
        fusedLocationListener.lastLocation
            .addOnSuccessListener { location ->
                presenter.loadCurrentData(location)
                presenter.loadForecastData(location)
            }
    }

    override fun currentDataLoaded(data: Response) {
        GlideLoaderUtils.loadImage(image, data.weather[0].icon)
        position.text = data.name
        title.text = data.weather[0].description
        temperature.text = data.getTemperature().toInt().toString() + " °C"
        wind.text = "Speed of wind = " + data.wind.speed.toString()
    }

    override fun forecastDataLoaded(data: ForecastResponse) = forecastAdapter.setItems(data.list)

    override fun inProgress(show: Boolean) {
        if (show) {
            container.visibility = View.GONE
            progress.visibility = View.VISIBLE
        } else {
            container.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }
    }
}
package com.example.weathertestapp.ui.forecast

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertestapp.R
import com.example.weathertestapp.domain.Response
import com.example.weathertestapp.extensions.GlideLoaderUtils
import kotlinx.android.synthetic.main.item_list_forecast.view.*

class ForecastAdapter: RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val forecastList = mutableListOf<Response>()

    override fun getItemCount() = forecastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecastList[position])
    }

    fun setItems(list: List<Response>) {
        forecastList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(item: Response) {
            GlideLoaderUtils.loadImage(itemView.image, item.weather[0].icon)
            itemView.title.text = item.weather[0].description
            itemView.temperature.text = item.getTemperature().toInt().toString() + " °C"
            itemView.wind.text = "Speed of wind = " + item.wind.speed.toString()
        }
    }
}
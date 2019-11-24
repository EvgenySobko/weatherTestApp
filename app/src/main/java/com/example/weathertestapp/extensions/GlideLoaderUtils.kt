package com.example.weathertestapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions

object GlideLoaderUtils {

    fun loadImage(imageView: ImageView, image: String) {
        Glide
            .with(imageView.context)
            .load(GlideUrl("https://openweathermap.org/img/w/$image.png"))
            .apply(
                RequestOptions()
                    .centerInside()
            )
            .thumbnail(0.1f)
            .into(imageView)
    }
}
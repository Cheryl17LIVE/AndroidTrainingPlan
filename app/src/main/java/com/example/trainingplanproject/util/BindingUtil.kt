package com.example.trainingplanproject.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trainingplanproject.R

@BindingAdapter("imageUrl")
fun ImageView.glideLoadImg(imgUrl: String?) {
    Glide.with(this.context)
        .load(imgUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
        )
        .into(this)
}

package com.hannibalprojects.sampleproject.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Viewbinder {

    companion object {
        @JvmStatic
        @BindingAdapter("app:loadImage")
        fun bindImageUrl(imageView: ImageView, url: String?) {
            if (url != null) {
                Glide.with(imageView.context).load(url).into(imageView)
            }
        }
    }
}
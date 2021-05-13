package com.alansoft.pokedex.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * Created by LEE MIN KYU on 2021/05/10
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImg")
    fun bindLaodImg(view: ImageView, url: String) {
        view.loadWithThumbnail(url)
    }
}
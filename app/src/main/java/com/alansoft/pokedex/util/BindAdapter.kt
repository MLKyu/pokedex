package com.alansoft.pokedex.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * Created by LEE MIN KYU on 2021/05/10
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */

@BindingAdapter("loadImg")
fun laodImg(view: ImageView, url: String) {
    view.loadWithThumbnail(url)
}
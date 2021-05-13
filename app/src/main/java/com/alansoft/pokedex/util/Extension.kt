package com.alansoft.pokedex.util

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by LEE MIN KYU on 2021/05/10
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
fun ImageView.loadWithThumbnail(uri: String?) {
    Glide.with(context)
        .load(uri)
        .thumbnail(0.1f)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Fragment.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
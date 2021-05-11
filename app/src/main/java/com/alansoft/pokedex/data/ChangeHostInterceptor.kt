package com.alansoft.pokedex.data

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by LEE MIN KYU on 2021/05/11
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
object ChangeHostInterceptor : Interceptor {
    private var scheme: String? = null
    private var host: String? = null

    fun setInterceptor(url: String) {
        val httpUrl = url.toHttpUrlOrNull()
        httpUrl?.let {
            scheme = it.scheme
            host = it.host
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original: Request = chain.request()
        val tempScheme = scheme
        val tempHost = host
        if (!tempScheme.isNullOrEmpty() && !tempHost.isNullOrEmpty()) {
            val newUrl: HttpUrl = original.url.newBuilder()
                .scheme(tempScheme)
                .host(tempHost)
                .build()
            original = original.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(original)
    }
}
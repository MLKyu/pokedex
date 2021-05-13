package com.alansoft.pokedex.network

import com.alansoft.pokedex.data.Resource
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by LEE MIN KYU on 2021/05/13
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val apiResponse = Resource.error(exception)
        MatcherAssert.assertThat(apiResponse.exception, CoreMatchers.`is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse = Resource.success("foo")
        MatcherAssert.assertThat(apiResponse.data, CoreMatchers.`is`("foo"))
    }
}
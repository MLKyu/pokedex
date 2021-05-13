package com.alansoft.pokedex

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Created by LEE MIN KYU on 2021/05/12
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MapsActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
}
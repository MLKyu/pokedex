package com.alansoft.pokedex.data

import com.alansoft.pokedex.data.api.DemoApi
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchDataSource @Inject constructor(private val demoApi: DemoApi) {
    suspend fun getPokemonName() = demoApi.getPokemonName()
    suspend fun getPokemonLocations() = demoApi.getPokemonLocations()
}
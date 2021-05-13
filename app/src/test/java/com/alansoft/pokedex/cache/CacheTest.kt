package com.alansoft.pokedex.cache

import com.alansoft.pokedex.data.SearchCacheDataSource
import com.alansoft.pokedex.data.model.PokemonNameResponse
import com.alansoft.pokedex.util.MockUtil.mockPokemon
import com.alansoft.pokedex.util.MockUtil.mockPokemonList
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by LEE MIN KYU on 2021/05/13
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@RunWith(JUnit4::class)
class CacheTest {

    private lateinit var cache: SearchCacheDataSource

    @Before
    fun setup() {
        cache = SearchCacheDataSource()
    }

    @Test
    fun pushAndLoadPokemonNameTest() = runBlocking {
        val mockDataList = mockPokemonList()
        cache.pushSearchResponse("이민규", PokemonNameResponse(mockDataList))

        val loadFromCache = cache.getSearchResponse("이민규")
        MatcherAssert.assertThat(loadFromCache.pokemons.toString(), `is`(mockDataList.toString()))

        val mockData = mockPokemon()
        MatcherAssert.assertThat(
            loadFromCache.pokemons?.get(0).toString(),
            `is`(mockData.toString())
        )
    }
}
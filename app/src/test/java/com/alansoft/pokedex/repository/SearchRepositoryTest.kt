package com.alansoft.pokedex.repository

import app.cash.turbine.test
import com.alansoft.pokedex.data.LocationCacheDataSource
import com.alansoft.pokedex.data.RemoteDataSource
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.SearchCacheDataSource
import com.alansoft.pokedex.data.api.PokeApi
import com.alansoft.pokedex.data.model.PokemonNameResponse
import com.alansoft.pokedex.util.MainCoroutinesRule
import com.alansoft.pokedex.util.MockUtil.mockPokemonList
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

/**
 * Created by LEE MIN KYU on 2021/05/13
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@RunWith(JUnit4::class)
class SearchRepositoryTest {

    private lateinit var repository: SearchRepository
    private lateinit var searchCache: SearchCacheDataSource
    private lateinit var locationCache: LocationCacheDataSource
    private lateinit var remote: RemoteDataSource
    private val pokeApi: PokeApi = mock()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        searchCache = SearchCacheDataSource()
        locationCache = LocationCacheDataSource()
        remote = RemoteDataSource(pokeApi)
        repository = SearchRepository(
            locationCache,
            searchCache,
            remote
        )
    }

    @ExperimentalTime
    @Test
    fun getPokemonNameFromNetworkTest() = runBlocking {
        val mockData = PokemonNameResponse(mockPokemonList())
        whenever(searchCache.getSearchResponse("이민규")).thenReturn(mockData.copy())
        whenever(pokeApi.getPokemonName()).thenReturn(mockData)

        repository.getPokemonName(
            "이민규",
            {},
            { _, _ -> mockPokemonList() })
            .test(2.seconds) {
                val expectItem: Resource<PokemonNameResponse> = expectItem()
                Assert.assertEquals((expectItem as Resource.Success).data.pokemons?.get(0)?.id, 1)
                Assert.assertEquals(
                    expectItem.data.pokemons?.get(0)?.names,
                    "이민규"
                )
                assertEquals(expectItem, mockPokemonList()[0])
                expectComplete()
            }

        verify(searchCache, atLeastOnce()).getSearchResponse("이민규")
        verify(pokeApi, atLeastOnce()).getPokemonName()
        verify(searchCache, atLeastOnce()).pushSearchResponse("이민규", mockData)
        verifyNoMoreInteractions(pokeApi)
    }

}
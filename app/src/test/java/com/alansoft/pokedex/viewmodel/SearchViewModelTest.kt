package com.alansoft.pokedex.viewmodel

import app.cash.turbine.test
import com.alansoft.pokedex.data.LocationCacheDataSource
import com.alansoft.pokedex.data.RemoteDataSource
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.SearchCacheDataSource
import com.alansoft.pokedex.data.api.PokeApi
import com.alansoft.pokedex.data.model.PokemonNameResponse
import com.alansoft.pokedex.repository.SearchRepository
import com.alansoft.pokedex.ui.search.SearchViewModel
import com.alansoft.pokedex.util.MainCoroutinesRule
import com.alansoft.pokedex.util.MockUtil
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var repository: SearchRepository
    private var searchCache: SearchCacheDataSource = SearchCacheDataSource()
    private var locationCache: LocationCacheDataSource = LocationCacheDataSource()
    private val pokeApi: PokeApi = mock()
    private var remote: RemoteDataSource = RemoteDataSource(pokeApi)

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        repository = SearchRepository(
            locationCache,
            searchCache,
            remote
        )
        viewModel = SearchViewModel(repository)
    }

    @ExperimentalTime
    @Test
    fun getPokemonNameTest() = runBlocking {
        val mockData = PokemonNameResponse(MockUtil.mockPokemonList())
        whenever(searchCache.getSearchResponse("이민규")).thenReturn(mockData.copy())
        whenever(pokeApi.getPokemonName()).thenReturn(mockData)

        repository.getPokemonName(
            "이민규",
            {},
            { _, _ -> MockUtil.mockPokemonList() })
            .test(2.seconds) {
                val expectItem: Resource<PokemonNameResponse> = expectItem()
                Assert.assertEquals((expectItem as Resource.Success).data.pokemons?.get(0)?.id, 1)
                Assert.assertEquals(
                    expectItem.data.pokemons?.get(0)?.names,
                    "이민규"
                )
                junit.framework.Assert.assertEquals(expectItem, MockUtil.mockPokemonList()[0])
                expectComplete()
            }

        viewModel.setQuery("이민규")

        verify(searchCache, atLeastOnce()).getSearchResponse("이민규")
    }
}
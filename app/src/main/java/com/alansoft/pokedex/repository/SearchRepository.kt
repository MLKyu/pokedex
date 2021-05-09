package com.alansoft.pokedex.repository

import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.SearchDataSource
import com.alansoft.pokedex.data.response.PokemonNameResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import java.io.IOException
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchRepository @Inject constructor(
    private val remote: SearchDataSource
) {
    fun getPokemonName(): Flow<Resource<PokemonNameResponse>> = flow {
        emit(Resource.loading())

        val response: PokemonNameResponse = remote.getPokemonName()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                Resource.success(response)
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }
}
package com.alansoft.pokedex.repository

import androidx.annotation.WorkerThread
import com.alansoft.pokedex.data.RemoteDataSource
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchRepository @Inject constructor(private val remote: RemoteDataSource) {
    @WorkerThread
    fun getPokemonName(
        query: String,
        onSearch: (query: String, List<Name?>) -> List<Name?>
    ): Flow<Resource<PokemonNameResponse>> = flow {
        emit(Resource.loading())
        val response: PokemonNameResponse = remote.getPokemonName()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                val lowerQuery = query.toLowerCase()
                response.pokemons?.let {
                    val list = onSearch(lowerQuery, it)
                    if (list.isNullOrEmpty()) {
                        Resource.empty()
                    } else {
                        Resource.success(PokemonNameResponse(list))
                    }
                } ?: Resource.empty()
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getPokemonLocation(
        id: Long,
        findLoacation: (id: Long, data: List<Location?>) -> List<Location?>
    ): Flow<Resource<List<Location?>>> = flow {
        emit(Resource.loading())
        val response: PokemonLocationResponse = remote.getPokemonLocations()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                val location = findLoacation(id, response.pokemons)
                if (location.isNullOrEmpty()) {
                    Resource.empty()
                } else {
                    Resource.success(location)
                }
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getPokemonInfo(id: Pair<Long, String>): Flow<Resource<PokemonDetailResponse>> = flow {
        emit(Resource.loading())
        val response: PokemonDetailResponse = remote.getPokemonInfo(id.first)
        response.name = id.second
        emit(Resource.success(response))
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)
}
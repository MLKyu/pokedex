package com.alansoft.pokedex.repository

import androidx.annotation.WorkerThread
import com.alansoft.pokedex.data.RemoteDataSource
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.model.Location
import com.alansoft.pokedex.data.model.PokemonDetailResponse
import com.alansoft.pokedex.data.model.PokemonLocationResponse
import com.alansoft.pokedex.data.model.PokemonNameResponse
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
    fun getPokemonName(query: String): Flow<Resource<PokemonNameResponse>> = flow {
        emit(Resource.loading())
        val response: PokemonNameResponse = remote.getPokemonName()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                val list = response.pokemons?.filter { filter ->
                    !filter?.names.isNullOrEmpty() && filter?.names?.find {
                        it?.contains(query) ?: false
                    } != null
                }

                if (list.isNullOrEmpty()) {
                    Resource.empty()
                } else {
                    Resource.success(PokemonNameResponse(list))
                }
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getPokemonLocation(id: Long): Flow<Resource<Location>> = flow {
        emit(Resource.loading())
        val response: PokemonLocationResponse = remote.getPokemonLocations()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                val location: Location? = response.pokemons.find { id == it?.id }
                if (location == null) {
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
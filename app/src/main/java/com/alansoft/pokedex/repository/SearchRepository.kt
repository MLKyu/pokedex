package com.alansoft.pokedex.repository

import androidx.annotation.WorkerThread
import com.alansoft.pokedex.data.LocationCacheDataSource
import com.alansoft.pokedex.data.RemoteDataSource
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.SearchCacheDataSource
import com.alansoft.pokedex.data.model.Name
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
class SearchRepository @Inject constructor(
    private val locationCache: LocationCacheDataSource,
    private val searchCache: SearchCacheDataSource,
    private val remote: RemoteDataSource
) {
    @WorkerThread
    fun getPokemonName(
        query: String,
        onLoading: (Boolean) -> Unit,
        onSearch: (query: String, List<Name?>) -> List<Name?>
    ): Flow<Resource<PokemonNameResponse>> = flow {
        val response: PokemonNameResponse
        emit(
            if (searchCache.isExistAndFresh(query)) {
                response = searchCache.getSearchResponse(query)
                Resource.success(response)
            } else {
                response = remote.getPokemonName()
                if (response.pokemons.isNullOrEmpty()) {
                    Resource.empty()
                } else {
                    val lowerQuery = query.toLowerCase()
                    response.pokemons?.let {
                        val list = onSearch(lowerQuery, it)
                        if (list.isNullOrEmpty()) {
                            Resource.empty()
                        } else {
                            Resource.pushAndSuccess(PokemonNameResponse(list)) { response ->
                                searchCache.pushSearchResponse(query, response)
                            }
                        }
                    } ?: Resource.empty()
                }
            }
        )
    }.onStart {
        onLoading(true)
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.onCompletion {
        onLoading(false)
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getPokemonLocation(
        id: Long,
        findLocation: (id: Long, data: PokemonLocationResponse) -> PokemonLocationResponse
    ): Flow<Resource<PokemonLocationResponse>> = flow {
        val response: PokemonLocationResponse
        emit(
            if (locationCache.isExistAndFresh(id)) {
                response = locationCache.getResponse(id)
                Resource.success(response)
            } else {
                response = remote.getPokemonLocations()
                if (response.pokemons.isNullOrEmpty()) {
                    Resource.empty()
                } else {
                    val location = findLocation(id, response)
                    if (location.pokemons.isNullOrEmpty()) {
                        Resource.empty()
                    } else {
                        Resource.pushAndSuccess(location) {
                            locationCache.pushResponse(id, location)
                        }
                    }
                }
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getPokemonInfo(
        id: Triple<Long, String, Int>,
        onLoading: (Boolean) -> Unit
    ): Flow<Resource<PokemonDetailResponse>> =
        flow<Resource<PokemonDetailResponse>> {
            val response: PokemonDetailResponse = remote.getPokemonInfo(id.first)
            response.name = id.second
            emit(Resource.success(response))
        }.onStart {
            onLoading(true)
        }.onCompletion {
            onLoading(false)
        }.retry(2) { cause ->
            cause is IOException
        }.catch { e ->
            emit(Resource.error(e))
        }.flowOn(Dispatchers.IO)
}
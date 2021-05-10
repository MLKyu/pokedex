package com.alansoft.pokedex.repository

import androidx.annotation.WorkerThread
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.SearchDataSource
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
    private val searchRemote: SearchDataSource
) {
    @WorkerThread
    fun getPokemonName(query: String): Flow<Resource<PokemonNameResponse>> = flow {
        emit(Resource.loading())
        val response: PokemonNameResponse = searchRemote.getPokemonName()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                // 검색어를 입력하면, 이름에 검색어를 포함하는 포켓몬의 목록을 표시한다.
                //a. 검색어 포함 여부를 판단할 때, 영문 대소문자를 구분하지 않는다. 즉,
                //Pikachu는 pi 나 Pi 로 검색한 결과에 모두 포함되어야 한다.
                //b. 하나의 포켓몬은 여러개의 이름을 가질 수 있다. 이 경우, 목록엔 검색어에
                //해당하는 이름을 표시해야 한다. 즉, “피카" 로 검색했을 경우, 목록에는
                //“Pikachu” 가 아닌 “피카츄" 가 표시되어야 한다.
                Resource.success(response)
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getPokemonLocation(id: Long): Flow<Resource<PokemonLocationResponse>> = flow {
        emit(Resource.loading())
        val response: PokemonLocationResponse = searchRemote.getPokemonLocations()
        emit(
            if (response.pokemons.isNullOrEmpty()) {
                Resource.empty()
            } else {
                // id 와 일치하는 로케이션 찾아야 함
                Resource.success(response)
            }
        )
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)
}
package com.alansoft.pokedex.data

import com.alansoft.pokedex.data.api.PokeApi
import com.alansoft.pokedex.data.model.PokemonDetailResponse
import com.alansoft.pokedex.data.model.PokemonLocationResponse
import com.alansoft.pokedex.data.model.PokemonNameResponse
import com.alansoft.pokedex.di.NetworkModule
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class RemoteDataSource @Inject constructor(private val pokeApi: PokeApi) {
    suspend fun getPokemonName(): PokemonNameResponse {
        ChangeHostInterceptor.setInterceptor(NetworkModule.MOCK_HOST)
        return pokeApi.getPokemonName()
    }

    suspend fun getPokemonLocations(): PokemonLocationResponse {
        ChangeHostInterceptor.setInterceptor(NetworkModule.MOCK_HOST)
        return pokeApi.getPokemonLocations()
    }

    suspend fun getPokemonInfo(id: Long): PokemonDetailResponse {
        ChangeHostInterceptor.setInterceptor(NetworkModule.POKE_HOST)
        return pokeApi.getPokemonInfo(id)
    }
}
package com.alansoft.pokedex.data.api

import com.alansoft.pokedex.data.model.PokemonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
interface PokeApi {
    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemonName(@Path("id") id: Long): PokemonDetailResponse
}
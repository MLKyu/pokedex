package com.alansoft.pokedex.data.api

import com.alansoft.pokedex.data.model.PokemonDetailResponse
import com.alansoft.pokedex.data.model.PokemonLocationResponse
import com.alansoft.pokedex.data.model.PokemonNameResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
interface PokeApi {

    @GET("/pokemon_name")
    suspend fun getPokemonName(): PokemonNameResponse

    @GET("/pokemon_locations")
    suspend fun getPokemonLocations(): PokemonLocationResponse

    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemonInfo(@Path("id") id: Long): PokemonDetailResponse
}
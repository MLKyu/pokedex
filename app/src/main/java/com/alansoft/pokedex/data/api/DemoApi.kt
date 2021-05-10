package com.alansoft.pokedex.data.api

import com.alansoft.pokedex.data.model.PokemonLocationResponse
import com.alansoft.pokedex.data.model.PokemonNameResponse
import retrofit2.http.GET

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
interface DemoApi {
    @GET("/pokemon_name")
    suspend fun getPokemonName(): PokemonNameResponse

    @GET("/pokemon_locations")
    suspend fun getPokemonLocations(): PokemonLocationResponse


//    ● 포켓몬 이름 - id 매핑 API: https://demo0928971.mockable.io/pokemon_name
//    ● 포켓몬 별 서식지 정보 API: https://demo0928971.mockable.io/pokemon_locations
//    ● 포켓몬 상세 정보: https://pokeapi.co/api/v2/pokemon/{id }
//    ○ API 문서: https://pokeapi.co/docs/v2#pokemon
}
package com.alansoft.pokedex.util

import com.alansoft.pokedex.data.model.Name
import com.alansoft.pokedex.data.model.PokemonDetailResponse

/**
 * Created by LEE MIN KYU on 2021/05/13
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
object MockUtil {
    fun mockPokemon() = Name(
        listOf("이민규", "Alan")
    ).apply {
        id = 1
    }

    fun mockPokemonList() = listOf(mockPokemon(), mockPokemon())
    fun mockPokemonInfo() = PokemonDetailResponse(
        name = "이민규/Alan",
        height = 189,
        weight = 83,
        sprites = null
    ).apply {
        id = 1
    }
}
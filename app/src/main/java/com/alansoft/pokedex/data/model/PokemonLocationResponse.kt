package com.alansoft.pokedex.data.model

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
data class PokemonLocationResponse(
    val pokemons: List<Location?>?
)

data class Location(
    val lat: Long,
    val lan: Long

): Pokemons()
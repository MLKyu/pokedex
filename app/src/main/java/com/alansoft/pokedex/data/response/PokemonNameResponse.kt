package com.alansoft.pokedex.data.response

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
data class PokemonNameResponse(
    val pokemons: List<Name?>?
)

data class Name(
    val names: List<String?>?
) : Pokemons()

open class Pokemons {
    val id: Long = -1
}
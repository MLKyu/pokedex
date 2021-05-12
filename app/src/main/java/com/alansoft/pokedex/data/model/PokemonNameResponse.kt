package com.alansoft.pokedex.data.model

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
data class PokemonNameResponse(
    var pokemons: List<Name?>?
)

data class Name(
    val names: List<String?>?
) : Pokemons() {
    fun getName(): String {
        var namess = ""
        if (names.isNullOrEmpty()) {
            return namess
        }

        for (nameaa in names.withIndex()) {
            namess += "${nameaa.value}"
            if (nameaa.index != names.size - 1) {
                namess += "/"
            }
        }

        return namess
    }
}

open class Pokemons {
    var id: Long = -1
    fun getIdString(): String = String.format("#%03d", id)
}

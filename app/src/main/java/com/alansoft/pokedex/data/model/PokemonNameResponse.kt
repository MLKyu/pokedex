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
        var displayName = ""
        if (names.isNullOrEmpty()) {
            return displayName
        }

        for (name in names.withIndex()) {
            displayName += "${name.value}"
            if (name.index != names.size - 1) {
                displayName += "/"
            }
        }
        return displayName
    }
}

open class Pokemons {
    var id: Long = -1
    fun getIdString(): String = String.format("#%03d", id)
}

package com.alansoft.pokedex.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
data class PokemonLocationResponse(
    val pokemons: List<Location?>?
)

@Parcelize
data class Location(
    val lat: String?,
    val lan: String?
) : Pokemons(), Parcelable
package com.alansoft.pokedex.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Parcelize
data class PokemonLocationResponse(
    var pokemons: List<Location?>?
) : Parcelable

@Parcelize
data class Location(
    val lat: String?,
    val lng: String?
) : Pokemons(), Parcelable
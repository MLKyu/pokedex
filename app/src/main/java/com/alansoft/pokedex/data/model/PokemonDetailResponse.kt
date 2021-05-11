package com.alansoft.pokedex.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Parcelize
data class PokemonDetailResponse(
    var name: String?,
    val weight: Int,
    val height: Int,
    val sprites: Sprites?
) : Pokemons() {
    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("%.1f M", height.toFloat() / 10)
}

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_female")
    val backFemale: String?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale: String?,
    @SerializedName("front_female")
    val frontFemale: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?,
    val other: Other?,
) {
    fun getRepresentativeUri(): String? {
        return if (frontDefault != null) {
            frontDefault
        } else {
            null
        }
    }
}

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld?,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?,
)

data class DreamWorld(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)
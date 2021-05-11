package com.alansoft.pokedex.data.model

import android.os.Parcelable
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
) : Pokemons(), Parcelable {
    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("%.1f M", height.toFloat() / 10)
    fun getRepresentativeUri(): String? {
        if (sprites == null) {
            return ""
        }
        return sprites.run {
            if (frontDefault != null) {
                frontDefault
            } else {
                null
            }
        }
    }
}

@Parcelize
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
) : Parcelable

@Parcelize
data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld?,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?,
) : Parcelable

@Parcelize
data class DreamWorld(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?
) : Parcelable

@Parcelize
data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
) : Parcelable
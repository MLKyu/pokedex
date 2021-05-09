package com.alansoft.pokedex.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.alansoft.pokedex.R
import com.alansoft.pokedex.data.response.Pokemons
import com.alansoft.pokedex.ui.base.BaseListAdapter

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchListAdapter(
    itemCallback: ((Pokemons) -> Unit)?
) : BaseListAdapter<Pokemons>(itemCallback, DiffCallback()) {
    override fun getViewType(item: Pokemons): Int {
        return R.layout.item_pokemon
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Pokemons>() {
    override fun areItemsTheSame(oldItem: Pokemons, newItem: Pokemons): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemons, newItem: Pokemons): Boolean {
        return oldItem.equals(newItem)
    }
}

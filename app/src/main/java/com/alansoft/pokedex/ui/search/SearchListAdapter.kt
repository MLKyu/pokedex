package com.alansoft.pokedex.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.alansoft.pokedex.R
import com.alansoft.pokedex.data.model.Name
import com.alansoft.pokedex.ui.base.BaseListAdapter

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchListAdapter(
    itemCallback: ((Name) -> Unit)?
) : BaseListAdapter<Name>(itemCallback, DiffCallback()) {
    override fun getViewType(item: Name): Int {
        return R.layout.item_pokemon
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Name>() {
    override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean {
        return oldItem.equals(newItem)
    }
}

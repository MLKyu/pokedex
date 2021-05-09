package com.alansoft.pokedex.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
abstract class BaseListAdapter<M>(
    private val itemCallback: ((M) -> Unit)?,
    diffCallback: DiffUtil.ItemCallback<M>
) :
    ListAdapter<M, ViewHolder>(AsyncDifferConfig.Builder<M>(diffCallback).build()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(getItem(position))
    }

    protected abstract fun getViewType(item: M): Int

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding?.run {
            setVariable(BR.item, item)
            executePendingBindings()
            root.setOnClickListener {
                itemCallback?.invoke(item)
            }
        }
    }
}

class ViewHolder(parent: ViewGroup, @LayoutRes layout: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layout, parent, false)
) {
    var binding: ViewDataBinding? = try {
        DataBindingUtil.bind(itemView)
    } catch (e: Exception) {
        null
    }
}
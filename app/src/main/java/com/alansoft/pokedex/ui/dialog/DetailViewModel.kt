package com.alansoft.pokedex.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.model.Location
import com.alansoft.pokedex.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/10
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class DetailViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private val id = MutableStateFlow(-1L)

    val results: LiveData<Resource<Location>> = id
        .filter {
            it >= 0
        }.flatMapLatest {
            repository.getPokemonLocation(it)
        }.asLiveData()

    fun requestLocation(id: Long) {
        if (this.id.value != id) {
            this.id.value = id
        }
    }
}
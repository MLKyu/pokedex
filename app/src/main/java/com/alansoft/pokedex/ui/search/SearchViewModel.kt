package com.alansoft.pokedex.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.model.Name
import com.alansoft.pokedex.data.model.PokemonDetailResponse
import com.alansoft.pokedex.data.model.PokemonNameResponse
import com.alansoft.pokedex.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import java.util.*
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val _id = MutableStateFlow(Triple(-1L, "", 0))
    private val id: StateFlow<Triple<Long, String, Int>> = _id

    private var isLoading = MutableLiveData<Boolean>()

    val results: LiveData<Resource<PokemonNameResponse>> = query
        .filter {
            it.isNotEmpty()
        }.flatMapLatest {
            repository.getPokemonName(
                it,
                { isLoading.postValue(true) },
                { query, data -> search(query, data) })
        }.asLiveData()

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        query.value = input
    }

    fun search(query: String, pokemons: List<Name?>): List<Name?> {
        return pokemons.filter { filter ->
            !filter?.names.isNullOrEmpty() && filter?.names?.find {
                it?.contains(query, true) ?: false
            } != null
        }
    }

    fun requestDetail(id: Long, name: String) {
        if (this.id.value.first != id) {
            this._id.value = Triple(id, name, 0)
        } else {
            if (isLoading.value == false) {
                var count = this.id.value.third
                this._id.value = Triple(id, name, ++count)
            }
        }
    }

    val detailResult: LiveData<Resource<PokemonDetailResponse>> = id
        .filter {
            it.first >= 0
        }.flatMapLatest {
            repository.getPokemonInfo(it) { loading -> isLoading.value = loading }
        }.asLiveData()

}
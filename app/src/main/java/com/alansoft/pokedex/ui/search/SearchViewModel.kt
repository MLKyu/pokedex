package com.alansoft.pokedex.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alansoft.pokedex.data.Resource
import com.alansoft.pokedex.data.response.PokemonNameResponse
import com.alansoft.pokedex.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    val results: LiveData<Resource<PokemonNameResponse>> = query
        .filter {
            it.isNotEmpty()
        }.flatMapLatest {
            repository.getPokemonName()
        }.asLiveData()

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == query.value) {
            return
        }
        if (input.isNotBlank()) {
            query.value = input
        }
    }
}
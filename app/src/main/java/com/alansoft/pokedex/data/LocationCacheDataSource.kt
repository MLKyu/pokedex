package com.alansoft.pokedex.data

import com.alansoft.pokedex.data.model.PokemonLocationResponse
import java.util.*
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/05/12
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class LocationCacheDataSource @Inject constructor() {
    private val cached: LinkedList<Data> = LinkedList()

    fun pushResponse(id: Long, queryResponse: PokemonLocationResponse) {
        if (cached.size >= 5) {
            cached.removeFirst()
        }
        cached.addLast(Data(id, queryResponse))
    }

    fun getResponse(id: Long): PokemonLocationResponse {
        return cached.first { it.id == id }.response.copy()
    }

    fun isExistAndFresh(id: Long): Boolean {
        val index = isExist(id)
        if (index == -1) {
            return false
        }
        return isFresh(index)
    }

    private fun isExist(id: Long): Int {
        return cached.indexOfFirst { it.id == id }
    }

    private fun isFresh(index: Int): Boolean {
        if (cached[index].isFresh()) {
            return true
        }
        cached.removeAt(index)
        return false
    }

    private inner class Data(
        val id: Long,
        val response: PokemonLocationResponse
    ) {
        val createdAt: Long = System.currentTimeMillis()

        fun isFresh(): Boolean {
            val current = System.currentTimeMillis()
            return (current - createdAt) < timeout
        }
    }

    companion object {
        private const val timeout: Long = 1 * 60 * 1000 // 1분
    }
}
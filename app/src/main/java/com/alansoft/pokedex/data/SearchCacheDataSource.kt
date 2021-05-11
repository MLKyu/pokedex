//package com.alansoft.pokedex.data
//
//import com.alansoft.pokedex.data.model.PokemonResponse
//import java.util.*
//import javax.inject.Inject
//
///**
// * Created by LEE MIN KYU on 2021/04/26
// * Copyright © 2021 Dreamus Company. All rights reserved.
// */
//class CacheDataSource
//@Inject constructor() {
//    private val cached: LinkedList<Data> = LinkedList()
//
//    fun pushSearchResponse(queryResponse: PokemonResponse) {
//        if (cached.size >= 1) {
//            cached.removeFirst()
//        }
//        cached.addLast(Data(queryResponse))
//    }
//
////    fun <T> getSearchResponse(service: Class<T>): PokemonResponse {
////        return cached.first { it is  }.queryResponse.copy()
////    }
//
////    fun isExistAndFresh(query: String, page: Int): Boolean {
////        val index = isExist(query, page)
////        if (index == -1) {
////            return false
////        }
////        return isFresh(index)
////    }
//
////    private fun isExist(query: String, page: Int): Int {
////        return cached.indexOfFirst { it.query == query && it.page == page }
////    }
//
//    private fun isFresh(index: Int): Boolean {
//        if (cached[index].isFresh()) {
//            return true
//        }
//        cached.removeAt(index)
//        return false
//    }
//
//
//    private inner class Data(
//        service: Class<T>,
//        val queryResponse: PokemonResponse
//    ) {
//        val createdAt: Long = System.currentTimeMillis()
//
//        fun isFresh(): Boolean {
//            val current = System.currentTimeMillis()
//            return (current - createdAt) < timeout
//        }
//    }
//
//    companion object {
//        private const val timeout: Long = 1 * 60 * 1000 // 1분
//    }
//}
package com.alansoft.pokedex.di

import com.alansoft.pokedex.data.SearchDataSource
import com.alansoft.pokedex.data.api.DemoApi
import com.alansoft.pokedex.repository.SearchRepository
import com.alansoft.pokedex.ui.search.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Singleton
    @Provides
    fun provideSearchDataSource(demoApi: DemoApi) =
        SearchDataSource(demoApi)

    @Singleton
    @Provides
    fun provideSearchRepository(
        remote: SearchDataSource
    ) =
        SearchRepository(remote)

    @Singleton
    @Provides
    fun provideSearchViewModel(
        repository: SearchRepository
    ) = SearchViewModel(repository)
}
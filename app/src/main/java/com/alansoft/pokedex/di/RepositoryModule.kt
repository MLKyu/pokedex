package com.alansoft.pokedex.di

import com.alansoft.pokedex.data.RemoteDataSource
import com.alansoft.pokedex.data.api.PokeApi
import com.alansoft.pokedex.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideSearchDataSource(demoApi: PokeApi) = RemoteDataSource(demoApi)

    @ViewModelScoped
    @Provides
    fun provideSearchRepository(remote: RemoteDataSource) = SearchRepository(remote)
}
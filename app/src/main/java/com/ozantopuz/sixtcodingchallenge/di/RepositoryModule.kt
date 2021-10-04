package com.ozantopuz.sixtcodingchallenge.di

import com.ozantopuz.sixtcodingchallenge.domain.repository.CarsRepository
import com.ozantopuz.sixtcodingchallenge.domain.repository.CarsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCarRepository(repository: CarsRepositoryImpl): CarsRepository
}
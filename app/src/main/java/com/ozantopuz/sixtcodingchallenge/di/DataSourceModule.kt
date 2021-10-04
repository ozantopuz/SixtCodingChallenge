package com.ozantopuz.sixtcodingchallenge.di

import com.ozantopuz.sixtcodingchallenge.data.datasource.CarsRemoteDataSource
import com.ozantopuz.sixtcodingchallenge.data.datasource.CarsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCarRemoteDataSource(remoteDataSource: CarsRemoteDataSourceImpl): CarsRemoteDataSource
}
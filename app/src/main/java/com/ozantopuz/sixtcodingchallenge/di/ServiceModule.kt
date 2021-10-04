package com.ozantopuz.sixtcodingchallenge.di

import com.ozantopuz.sixtcodingchallenge.data.service.CarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideCarService(retrofit: Retrofit): CarService = retrofit.create()
}
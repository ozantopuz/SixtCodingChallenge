package com.ozantopuz.sixtcodingchallenge.di

import com.ozantopuz.sixtcodingchallenge.util.usecase.UseCase
import com.ozantopuz.sixtcodingchallenge.domain.usecase.CarsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindCarUseCase(useCase: CarsUseCase): UseCase
}
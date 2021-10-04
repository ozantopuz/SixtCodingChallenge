package com.ozantopuz.sixtcodingchallenge.domain.repository

import com.ozantopuz.sixtcodingchallenge.data.datasource.CarsRemoteDataSource
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CarsRemoteDataSource
) : CarsRepository {

    override suspend fun fetchCars(): ArrayList<Car> = remoteDataSource.fetchCars()
}
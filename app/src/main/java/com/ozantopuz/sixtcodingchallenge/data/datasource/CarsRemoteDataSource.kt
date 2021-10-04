package com.ozantopuz.sixtcodingchallenge.data.datasource

import com.ozantopuz.sixtcodingchallenge.data.entity.Car

interface CarsRemoteDataSource {
    suspend fun fetchCars(): ArrayList<Car>
}
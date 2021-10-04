package com.ozantopuz.sixtcodingchallenge.domain.repository

import com.ozantopuz.sixtcodingchallenge.data.entity.Car

interface CarsRepository {
    suspend fun fetchCars(): ArrayList<Car>

}
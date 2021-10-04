package com.ozantopuz.sixtcodingchallenge.data.datasource

import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.data.service.CarService
import javax.inject.Inject

class CarsRemoteDataSourceImpl @Inject constructor(
    private val service: CarService
) : CarsRemoteDataSource {

    override suspend fun fetchCars(): ArrayList<Car> = service.getCars()

}
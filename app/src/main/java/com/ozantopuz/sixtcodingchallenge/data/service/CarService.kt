package com.ozantopuz.sixtcodingchallenge.data.service

import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import retrofit2.http.GET

interface CarService {

    @GET("cars")
    suspend fun getCars(): ArrayList<Car>
}
package com.ozantopuz.sixtcodingchallenge.domain.usecase

import com.ozantopuz.sixtcodingchallenge.data.Result
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.domain.repository.CarsRepository
import com.ozantopuz.sixtcodingchallenge.util.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CarsUseCase @Inject constructor(
    private val repository: CarsRepository
) : UseCase.FlowUseCase<ArrayList<Car>> {

    override fun execute(): Flow<Result<ArrayList<Car>>> {
        return flow {
            val response = getCars()
            emit(Result.Success(response))
        }.catch { throwable ->
            Result.Error(Exception(throwable))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getCars() = coroutineScope {
        val response: ArrayList<Car> = try {
            repository.fetchCars()
        } catch (e: Exception) {
            arrayListOf()
        }
        response
    }
}
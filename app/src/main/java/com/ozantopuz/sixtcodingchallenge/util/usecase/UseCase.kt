package com.ozantopuz.sixtcodingchallenge.util.usecase

import com.ozantopuz.sixtcodingchallenge.data.Result
import kotlinx.coroutines.flow.Flow

interface UseCase {

    @FunctionalInterface
    interface FlowUseCase<out T> : UseCase {

        fun execute(): Flow<Result<T>>
    }
}
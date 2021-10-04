package com.ozantopuz.sixtcodingchallenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.ozantopuz.sixtcodingchallenge.Constants
import com.ozantopuz.sixtcodingchallenge.data.entity.CarFactory
import com.ozantopuz.sixtcodingchallenge.rule.CoroutinesTestRule
import com.ozantopuz.sixtcodingchallenge.rule.LifeCycleTestOwner
import com.ozantopuz.sixtcodingchallenge.MainViewModel
import com.ozantopuz.sixtcodingchallenge.data.Result
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.domain.usecase.CarsUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var carsUseCase: CarsUseCase

    @RelaxedMockK
    private lateinit var carsObserver: Observer<ArrayList<Car>>

    @RelaxedMockK
    private lateinit var errorObserver: Observer<String>

    private lateinit var viewModel: MainViewModel
    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        viewModel = MainViewModel(carsUseCase)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()

        viewModel.carsLiveData.removeObserver(carsObserver)
        viewModel.carsLiveData.removeObservers(lifeCycleTestOwner)

        viewModel.errorLiveData.removeObserver(errorObserver)
        viewModel.errorLiveData.removeObservers(lifeCycleTestOwner)

        unmockkAll()
        clearAllMocks()
    }

    @Test
    fun test_fetchCars_returns_success_data() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given
            coEvery {
                carsUseCase.execute()
            }.coAnswers {
                flowOf(Result.Success(arrayListOf(CarFactory.getCar())))
            }

            lifeCycleTestOwner.onResume()

            // When
            viewModel.fetchCars()
            val carsValue = viewModel.carsLiveData.value
            val errorValue = viewModel.errorLiveData.value

            // Then
            Truth.assertThat(carsValue).isNotNull()
            Truth.assertThat(errorValue).isNull()

            coVerify(exactly = 1) { carsUseCase.execute() }
            confirmVerified(carsUseCase)
        }
    }

    @Test
    fun test_fetchPersonalContract_returns_error() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given
            coEvery {
                carsUseCase.execute()
            }.coAnswers {
                flowOf(Result.Error(Exception(Constants.EXCEPTION_MESSAGE)))
            }

            lifeCycleTestOwner.onResume()

            // When
            viewModel.fetchCars()
            val carsValue = viewModel.carsLiveData.value
            val errorValue = viewModel.errorLiveData.value

            // Then
            Truth.assertThat(carsValue).isNull()
            Truth.assertThat(errorValue).isNotNull()

            coVerify(exactly = 1) { carsUseCase.execute() }
            confirmVerified(carsUseCase)
        }
    }
}
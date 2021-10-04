package com.ozantopuz.sixtcodingchallenge.data.datasource

import com.google.common.truth.Truth
import com.ozantopuz.sixtcodingchallenge.data.entity.CarFactory
import com.ozantopuz.sixtcodingchallenge.rule.CoroutinesTestRule
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.data.service.CarService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarsRemoteDataSourceImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    lateinit var carService: CarService

    private lateinit var carsRemoteDataSource: CarsRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        carsRemoteDataSource = CarsRemoteDataSourceImpl(carService)
    }

    @After
    fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }

    @Test
    fun test_fetchCars_returns_the_expected_data() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given
            coEvery {
                carService.getCars()
            }.coAnswers {
                arrayListOf(CarFactory.getCar())
            }

            // When
            val result: ArrayList<Car> = carsRemoteDataSource.fetchCars()

            // Then
            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result).isEqualTo(arrayListOf(CarFactory.getCar()))

            coVerify(exactly = 1) { carService.getCars() }
            confirmVerified(carService)
        }
    }

    @Test
    fun test_fetchCarsContract_returns_error() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given
            coEvery {
                carService.getCars()
            }.coAnswers {
                arrayListOf()
            }

            // When
            val result: ArrayList<Car> = carsRemoteDataSource.fetchCars()

            // Then
            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result).isEqualTo(arrayListOf<Car>())

            coVerify(exactly = 1) { carService.getCars() }
            confirmVerified(carService)
        }
    }
}
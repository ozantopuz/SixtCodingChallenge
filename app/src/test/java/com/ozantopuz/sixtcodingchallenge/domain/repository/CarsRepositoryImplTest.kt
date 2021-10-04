package com.ozantopuz.sixtcodingchallenge.domain.repository

import com.google.common.truth.Truth
import com.ozantopuz.sixtcodingchallenge.data.entity.CarFactory
import com.ozantopuz.sixtcodingchallenge.rule.CoroutinesTestRule
import com.ozantopuz.sixtcodingchallenge.data.datasource.CarsRemoteDataSource
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarsRepositoryImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    lateinit var carsRemoteDataSource: CarsRemoteDataSource

    private lateinit var carsRepository: CarsRepository


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        carsRepository = CarsRepositoryImpl(carsRemoteDataSource)
    }

    @After
    fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }

    @Test
    fun test_fetchCars_returns_success() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given
            coEvery {
                carsRemoteDataSource.fetchCars()
            }.coAnswers {
                arrayListOf(CarFactory.getCar())
            }

            // When
            val result: ArrayList<Car> = carsRepository.fetchCars()

            // Then
            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result).isEqualTo(arrayListOf(CarFactory.getCar()))

            coVerify(exactly = 1) { carsRemoteDataSource.fetchCars() }
            confirmVerified(carsRemoteDataSource)
        }
    }

    @Test
    fun test_fetchCars_returns_error() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given
            coEvery {
                carsRemoteDataSource.fetchCars()
            }.coAnswers {
                arrayListOf()
            }

            // When
            val result: ArrayList<Car> = carsRepository.fetchCars()

            // Then
            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result).isEqualTo(arrayListOf<Car>())

            coVerify(exactly = 1) { carsRemoteDataSource.fetchCars() }
            confirmVerified(carsRemoteDataSource)
        }
    }
}
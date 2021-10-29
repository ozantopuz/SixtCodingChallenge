package com.ozantopuz.sixtcodingchallenge.domain.usecase

import com.google.common.truth.Truth
import com.ozantopuz.sixtcodingchallenge.data.entity.CarFactory
import com.ozantopuz.sixtcodingchallenge.rule.CoroutinesTestRule
import com.ozantopuz.sixtcodingchallenge.util.test
import com.ozantopuz.sixtcodingchallenge.data.Result
import com.ozantopuz.sixtcodingchallenge.domain.repository.CarsRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarsUseCaseImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    lateinit var carsRepository: CarsRepository

    private lateinit var carsUseCase: CarsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery {
            carsRepository.fetchCars()
        } coAnswers {
            arrayListOf(CarFactory.getCar())
        }

        carsUseCase = CarsUseCase(carsRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }

    /*@Test
    fun test_execute_returns_the_expected_data() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            // Given

            // When
            val flow = carsUseCase.execute()

            // Then
            flow.test {
                expectItem().run {
                    Truth.assertThat(this).isNotNull()
                    Truth.assertThat(this).isInstanceOf(Result.Success::class.java)
                }
                expectComplete()
            }

            coVerify(exactly = 1) { carsRepository.fetchCars() }
            confirmVerified(carsRepository)
        }
    }*/
}
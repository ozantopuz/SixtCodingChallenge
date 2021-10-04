package com.ozantopuz.sixtcodingchallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozantopuz.sixtcodingchallenge.data.Result
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.domain.usecase.CarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CarsUseCase
) : ViewModel() {

    private val _carsLiveData = MutableLiveData<ArrayList<Car>>()
    val carsLiveData: LiveData<ArrayList<Car>>
        get() = _carsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun fetchCars() {
        viewModelScope.launch {
            useCase.execute().collect { result ->
                when (result) {
                    is Result.Error -> _errorLiveData.value = result.exception.localizedMessage
                    is Result.Success -> _carsLiveData.value = result.data ?: arrayListOf()
                }
            }
        }
    }
}
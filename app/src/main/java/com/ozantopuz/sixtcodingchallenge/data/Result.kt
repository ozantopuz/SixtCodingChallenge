package com.ozantopuz.sixtcodingchallenge.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>() {
        override fun toString() = "Exception: $exception"
    }
}
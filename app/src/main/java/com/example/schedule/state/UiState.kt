package com.example.schedule.state

sealed interface UiState<out T> {

    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Failed(val message: String) : UiState<Nothing>

    data object NoDataFound : UiState<Nothing>
    data class InternalServerError(val errorMessage: String) : UiState<Nothing>

    data object InternetError : UiState<Nothing>
}
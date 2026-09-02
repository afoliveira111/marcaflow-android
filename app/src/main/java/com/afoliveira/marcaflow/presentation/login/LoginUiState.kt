package com.afoliveira.marcaflow.presentation.login


sealed interface LoginUiState {

    data object Idle : LoginUiState

    data object Loading : LoginUiState

    data class Success(
        val token: String,
        val userName: String,
        val businessName: String
    ) : LoginUiState

    data class Error(
        val message: String
    ) : LoginUiState
}
package com.rayadev.presentation.auth

sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val errorMessage: String) : AuthUiState()
}


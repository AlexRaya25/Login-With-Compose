package com.rayadev.presentation.auth

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false
)
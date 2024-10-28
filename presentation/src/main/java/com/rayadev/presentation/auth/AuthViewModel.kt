package com.rayadev.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.domain.model.AuthResult
import com.rayadev.domain.model.User
import com.rayadev.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    init {
        checkIfUserLoggedIn()
    }

    private fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            _isUserLoggedIn.value = authUseCases.isUserLoggedIn()
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> handleLogin(event.email, event.password)
            is AuthEvent.Register -> handleRegister(event.username, event.email, event.password, event.confirmPassword)
            AuthEvent.LoginAnonymous -> handleAnonymousLogin()
        }
    }

    private fun handleLogin(email: String, password: String) {
        updateUiState { AuthUiState.Loading }
        viewModelScope.launch {
            try {
                val result = authUseCases.login(email, password)
                handleAuthResult(result, "Logged in successfully")
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleRegister(username: String, email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            updateUiState { AuthUiState.Error("Passwords do not match") }
            return
        }
        updateUiState { AuthUiState.Loading }
        viewModelScope.launch {
            try {
                val result = authUseCases.register(User(username, email, password))
                handleAuthResult(result, "Registered successfully")
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleAnonymousLogin() {
        updateUiState { AuthUiState.Loading }
        viewModelScope.launch {
            try {
                authUseCases.loginAnonymously()
                updateUiState { AuthUiState.Success("Logged in anonymously") }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleAuthResult(result: AuthResult, successMessage: String) {
        when (result) {
            is AuthResult.Success -> {
                updateUiState { AuthUiState.Success(successMessage) }
            }
            is AuthResult.Error -> {
                updateUiState { AuthUiState.Error(result.message) }
            }
        }
    }

    private fun handleError(e: Exception) {
        val errorMessage = e.message ?: "An unexpected error occurred"
        updateUiState { AuthUiState.Error(errorMessage) }
    }

    private fun updateUiState(update: () -> AuthUiState) {
        _uiState.value = update()
    }
}
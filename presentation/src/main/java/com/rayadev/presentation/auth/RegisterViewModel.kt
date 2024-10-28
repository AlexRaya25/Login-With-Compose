package com.rayadev.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.domain.model.User
import com.rayadev.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authUseCases.register(User(username, email, password))
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Registration failed")
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun registerAnonymous(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                authUseCases.loginAnonymously()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Anonymous login failed")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
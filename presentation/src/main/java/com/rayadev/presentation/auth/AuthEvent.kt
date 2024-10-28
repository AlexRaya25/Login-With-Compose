package com.rayadev.presentation.auth

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class Register(val username: String, val email: String, val password: String, val confirmPassword: String) : AuthEvent()
    data object LoginAnonymous : AuthEvent()
}

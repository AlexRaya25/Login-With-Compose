package com.rayadev.domain.usecase

import com.rayadev.domain.model.AuthResult
import com.rayadev.domain.model.User
import com.rayadev.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCases @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun register(user: User): AuthResult {
        return authRepository.register(user)
    }

    suspend fun login(email: String, password: String): AuthResult {
        return authRepository.login(email, password)
    }

    suspend fun loginAnonymously(): User {
        return authRepository.loginAnonymously()
    }

    suspend fun isUserLoggedIn(): Boolean {
        return authRepository.getCurrentUser() != null
    }
}


package com.rayadev.domain.repository

import com.rayadev.domain.model.AuthResult
import com.rayadev.domain.model.User

interface AuthRepository {
    suspend fun register(user: User): AuthResult
    suspend fun login(email: String, password: String): AuthResult
    suspend fun loginAnonymously(): User
    suspend fun getCurrentUser(): User?
}
package com.rayadev.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rayadev.domain.model.AuthResult
import com.rayadev.domain.model.User
import com.rayadev.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun register(user: User): AuthResult {
        return try {
            user.password?.let { auth.createUserWithEmailAndPassword(user.email, it).await() }
            val firebaseUser: FirebaseUser? = auth.currentUser
            AuthResult.Success(User(firebaseUser?.displayName ?: "", user.email, user.password))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Registration failed")
        }
    }

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser: FirebaseUser? = result.user
            AuthResult.Success(User(firebaseUser?.displayName ?: "", email, password))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed")
        }
    }

    override suspend fun loginAnonymously(): User {
        val result = auth.signInAnonymously().await()
        val firebaseUser: FirebaseUser? = result.user
        return User(firebaseUser?.displayName ?: "", firebaseUser?.email ?: "", "")
    }

    override suspend fun getCurrentUser(): User? {
        return FirebaseAuth.getInstance().currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }
}
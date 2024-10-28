package com.rayadev.domain.model

data class User(
    val id: String,
    val email: String,
    val password: String? = null
)
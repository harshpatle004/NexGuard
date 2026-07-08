package com.example.nexguard.feature.auth.domain

interface AuthRepository {


    suspend fun login(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun loginWithGoogle(
        idToken: String
    ): Result<Unit>


    suspend fun register(
        email: String,
        password: String
    ): Result<Unit>

    fun logout()

}
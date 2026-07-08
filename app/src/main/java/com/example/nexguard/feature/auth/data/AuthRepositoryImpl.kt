package com.example.nexguard.feature.auth.data

import com.example.nexguard.feature.auth.domain.AuthRepository

class AuthRepositoryImpl : AuthRepository{
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}
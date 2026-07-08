package com.example.nexguard.feature.auth.presentation

data class AuthState (
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null
)
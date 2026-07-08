package com.example.nexguard.feature.auth.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexguard.feature.auth.data.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val authRepository = AuthRepositoryImpl(
        firebaseAuth = FirebaseAuth.getInstance()
    )


    private val _authState = MutableStateFlow(AuthState())

    val authState: StateFlow<AuthState> =
        _authState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {

            _authState.value = AuthState(
                isLoading = true
            )

            val result = authRepository.login(
                email = email,
                password = password
            )

            _authState.value = if (result.isSuccess) {
                AuthState(
                    isLoading = false,
                    isAuthenticated = true
                )
            } else {
                AuthState(
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun register(
        email: String,
        password: String
    ){
        viewModelScope.launch {
            _authState.value = AuthState(
                isLoading = true
            )

            val result = authRepository.register(
                email = email,
                password = password
            )

            _authState.value = if(result.isSuccess){
                AuthState(
                    isLoading = false,
                    isAuthenticated = true
                )
            }else{
                AuthState(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }


        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthState(
                isLoading = true
            )

            val result = authRepository.loginWithGoogle(idToken)

            _authState.value = if (result.isSuccess) {
                AuthState(
                    isLoading = false,
                    isAuthenticated = true
                )
            } else {
                AuthState(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }
}
package com.example.nexguard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexguard.feature.auth.presentation.AuthViewModel
import com.example.nexguard.feature.auth.presentation.LoginScreen
import com.example.nexguard.feature.auth.presentation.RegisterScreen
import com.example.nexguard.feature.home.presentation.HomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            navController.navigate("home") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {



        composable("home") {
            HomeScreen()
        }

        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onSignUpClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }
    }
}
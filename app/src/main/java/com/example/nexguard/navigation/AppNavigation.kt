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
import com.example.nexguard.feature.camera.presentation.CameraScreen
import com.example.nexguard.feature.home.presentation.HomeScreen
import com.example.nexguard.feature.monitor.presentation.MonitorScreen
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

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
        startDestination = "home"
    ) {



        composable("home") {
            HomeScreen(
                onCameraClick = {
                    navController.navigate("cameraScreen")
                },
                onMonitorClick = {
                    navController.navigate("monitorScreen")
                }
            )
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

        composable("cameraScreen"){
                CameraScreen()
        }

        composable("monitorScreen"){
            MonitorScreen()
        }
    }
}


package com.example.nexguard.feature.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexguard.R
import com.example.nexguard.feature.auth.data.GoogleAuthClient
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onSignUpClick: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleAuthClient = remember(context) {
        GoogleAuthClient(context)
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var localError by remember {
        mutableStateOf<String?>(null)
    }

    val errorMessage = localError ?: authState.errorMessage

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.login_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                Text(
                    text = "NEX",
                    color = Color(0xFF2196F3),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "GUARD",
                    color = Color.White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = "Your World, Secured.",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    localError = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email")
                },
                placeholder = {
                    Text("example@gmail.com")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = loginTextFieldColors()
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    localError = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Password")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = "Show or hide password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = loginTextFieldColors()
            )

            Text(
                text = "Forgot Password?",
                color = Color(0xFF42A5F5),
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clickable {
                        // Forgot password next
                    }
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color(0xFFFF6B6B),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {
                    when {
                        email.isBlank() -> {
                            localError = "Please enter your email"
                        }

                        password.isBlank() -> {
                            localError = "Please enter your password"
                        }

                        else -> {
                            localError = null

                            authViewModel.login(
                                email = email.trim(),
                                password = password
                            )
                        }
                    }
                },
                enabled = !authState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                if (authState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(alpha = 0.25f)
                )

                Text(
                    text = "OR",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 14.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(alpha = 0.25f)
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Button(
                onClick = {
                    scope.launch {
                        val result = googleAuthClient.signIn()

                        if (result.isSuccess) {
                            val idToken = result.getOrNull()

                            if (idToken != null) {
                                authViewModel.loginWithGoogle(idToken)
                            } else {
                                localError =
                                    "Google sign-in did not return a token"
                            }
                        } else {
                            localError =
                                result.exceptionOrNull()?.message
                                    ?: "Google sign-in failed"
                        }
                    }
                },
                enabled = !authState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "G",
                        color = Color(0xFF4285F4),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "  Continue with Google",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append("Don't have an account? ")

                    withStyle(
                        SpanStyle(
                            color = Color(0xFF42A5F5),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("Sign up")
                    }
                },
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    onSignUpClick()
                }
            )
        }
    }
}



@Composable
private fun loginTextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,

        focusedBorderColor = Color(0xFF2196F3),
        unfocusedBorderColor = Color.White.copy(alpha = 0.25f),

        focusedLabelColor = Color(0xFF42A5F5),
        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),

        focusedLeadingIconColor = Color(0xFF42A5F5),
        unfocusedLeadingIconColor = Color.White.copy(alpha = 0.65f),

        focusedTrailingIconColor = Color.White,
        unfocusedTrailingIconColor = Color.White.copy(alpha = 0.65f),

        focusedPlaceholderColor = Color.White.copy(alpha = 0.4f),
        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.4f),

        focusedContainerColor =
            Color(0xFF151A20).copy(alpha = 0.75f),

        unfocusedContainerColor =
            Color(0xFF151A20).copy(alpha = 0.75f)
    )
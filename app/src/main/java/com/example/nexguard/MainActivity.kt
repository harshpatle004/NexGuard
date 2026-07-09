package com.example.nexguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nexguard.navigation.AppNavigation
import com.example.nexguard.ui.theme.NexGuardTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NexGuardTheme {
                AppNavigation()
            }
        }
    }
}
package com.rayadev.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rayadev.presentation.auth.AuthViewModel
import com.rayadev.presentation.navigation.AppNavigation
import com.rayadev.presentation.theme.LoginWithComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginWithComposeTheme{
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {
        val authViewModel: AuthViewModel = viewModel()
        val isUserLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AppNavigation(
                navController = rememberNavController(),
                isUserLoggedIn = isUserLoggedIn
            )
        }
    }
}
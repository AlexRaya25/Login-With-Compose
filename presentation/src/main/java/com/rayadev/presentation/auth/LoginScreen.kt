package com.rayadev.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadev.presentation.R
import com.rayadev.presentation.theme.BlueGradient
import com.rayadev.presentation.theme.DarkBlue
import com.rayadev.presentation.utils.CustomOutlinedTextField


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val emptyFieldsError = stringResource(id = R.string.error_empty_fields)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGradient)
    ) {
        if (uiState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.login_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            CustomOutlinedTextField(
                value = usernameOrEmail,
                onValueChange = { usernameOrEmail = it },
                label = stringResource(id = R.string.user_email),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = null, tint = Color.White)
                },
                isError = errorMessage.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(id = R.string.password),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null, tint = Color.White)
                },
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (errorMessage.isNotEmpty()) Color.Red else Color.White)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = errorMessage.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                enabled = !uiState.isLoading
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    stringResource(id = R.string.forgot_password),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (usernameOrEmail.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(usernameOrEmail, password)
                    } else {
                        errorMessage = emptyFieldsError
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                enabled = !uiState.isLoading
            ) {
                Text(
                    stringResource(id = R.string.login_title),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.White
                )

                Text(
                    text = stringResource(id = R.string.or),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                        viewModel.loginAnonymous()
                    },
                    modifier = Modifier.size(36.dp),
                    enabled = !uiState.isLoading
                ) {
                    Icon(painterResource(id = R.drawable.ic_anonymous), contentDescription = null, tint = Color.Unspecified)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToRegister, enabled = !uiState.isLoading) {
                Text(stringResource(id = R.string.no_account_register), color = Color.White)
            }

            if (errorMessage.isNotEmpty()) {
                LaunchedEffect(errorMessage) {
                    snackbarHostState.showSnackbar(errorMessage)
                    errorMessage = ""
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                Snackbar {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Filled.ErrorOutline,
                            contentDescription = stringResource(id = R.string.error),
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = snackbarData.visuals.message,
                            color = Color.White
                        )
                    }
                }
            }
        )
    }
    LaunchedEffect(uiState) {
        if (uiState.success) {
            onLoggedIn()
        }
    }
}
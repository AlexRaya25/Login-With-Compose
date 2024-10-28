package com.rayadev.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.navigation.NavController
import com.rayadev.presentation.R
import com.rayadev.presentation.theme.BlueGradient
import com.rayadev.presentation.theme.DarkBlue
import com.rayadev.presentation.utils.CustomOutlinedTextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController,
    onRegistered: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val passwordDontMatch = stringResource(id = R.string.passwords_dont_match)
    val completeAllFields = stringResource(id = R.string.complete_all_fields)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGradient)
    ) {
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                color = Color.White
            )
        }
        Column(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomOutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = stringResource(R.string.username),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null, tint = Color.White)
                },
                isError = username.isEmpty() && errorMessage.isNotEmpty(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = null, tint = Color.White)
                },
                isError = email.isEmpty() && errorMessage.isNotEmpty(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null, tint = Color.White)
                },
                isError = password.isEmpty() && errorMessage.isNotEmpty(),
                enabled = !isLoading,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (errorMessage.isNotEmpty()) Color.Red else Color.White)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(R.string.confirm_password),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null, tint = Color.White)
                },
                isError = confirmPassword.isEmpty() && errorMessage.isNotEmpty(),
                enabled = !isLoading,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (errorMessage.isNotEmpty()) Color.Red else Color.White)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                        if (password == confirmPassword) {
                            viewModel.register(username, email, password, onRegistered) { errorMessage = it }
                        } else {
                            errorMessage = passwordDontMatch
                        }
                    } else {
                        errorMessage = completeAllFields
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                enabled = !isLoading
            ) {
                Text(
                    stringResource(id = R.string.register),
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
                        viewModel.registerAnonymous(
                            onSuccess = {
                                navController.navigate("home")
                            },
                            onError = {
                            }
                        )
                    },
                    modifier = Modifier.size(36.dp),
                    enabled = !isLoading
                ) {
                    Icon(painterResource(id = R.drawable.ic_anonymous), contentDescription = null, tint = Color.Unspecified)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onNavigateToLogin, enabled = !isLoading) {
                Text(stringResource(id = R.string.already_account), color = Color.White)
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
                .padding(32.dp)
                .align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                Snackbar {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Filled.ErrorOutline,
                            contentDescription = stringResource(R.string.error),
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
}
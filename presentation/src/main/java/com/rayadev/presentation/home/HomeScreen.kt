package com.rayadev.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.rayadev.presentation.R
import com.rayadev.presentation.theme.BlueGradient
import com.rayadev.presentation.theme.DarkBlue
import com.rayadev.presentation.utils.LogoutConfirmationDialog
import com.rayadev.presentation.utils.TechnologyChip

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    var showLogoutDialog by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }

    val emailNotAvailable = stringResource(id = R.string.email_not_available)
    val username = stringResource(id = R.string.username)

    LaunchedEffect(Unit) {
        currentUser?.let {
            email = it.email ?: emailNotAvailable
            displayName = it.displayName ?: username
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.welcome, displayName),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = stringResource(id = R.string.technologies_used),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TechnologyChip(technologyName = stringResource(id = R.string.jetpack_compose))
                    TechnologyChip(technologyName = stringResource(id = R.string.kotlin))
                    TechnologyChip(technologyName = stringResource(id = R.string.mvvm))
                    TechnologyChip(technologyName = stringResource(id = R.string.hilt))
                    TechnologyChip(technologyName = stringResource(id = R.string.firebase))
                    TechnologyChip(technologyName = stringResource(id = R.string.room))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { showLogoutDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(stringResource(id = R.string.logout), color = Color.White, fontWeight = FontWeight.Bold)
            }

            if (showLogoutDialog) {
                LogoutConfirmationDialog(
                    onConfirm = {
                        auth.signOut()
                        showLogoutDialog = false
                        onLogout()
                    },
                    onDismiss = { showLogoutDialog = false }
                )
            }
        }
    }
}
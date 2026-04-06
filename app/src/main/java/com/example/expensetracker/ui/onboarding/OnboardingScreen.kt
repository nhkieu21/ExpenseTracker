package com.example.expensetracker.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onNavigateToDashboard: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onNavigateToDashboard()
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Expense Tracker 👋",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Let's set up your profile to start tracking your daily expenses.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { Text("Your Name") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                isError = uiState.isNameError,
                supportingText = {
                    if (uiState.isNameError) {
                        Text("Please enter your name")
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.monthlySalary,
                onValueChange = { viewModel.onSalaryChanged(it) },
                label = { Text("Monthly Salary") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.isSalaryError,
                supportingText = {
                    if (uiState.isSalaryError) {
                        Text("Please enter a valid monthly amount")
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.completeOnboarding() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
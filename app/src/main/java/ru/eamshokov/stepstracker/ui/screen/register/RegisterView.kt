package ru.eamshokov.stepstracker.ui.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.Modifier

@Composable
fun RegisterView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel:RegisterViewModel = hiltViewModel()
){
    val username: String by viewModel.username.collectAsState()
    val password: String by viewModel.password.collectAsState()
    val result = viewModel.registerResult.collectAsState(false)
    val error = viewModel.error.collectAsState()
    if (result.value) {
        navController.navigate("activity") {
            launchSingleTop = true
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Column(Modifier.padding(0.dp, 8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Name") },
                value = username,
                isError = error.value is RegisterViewModel.ErrorState.UsernameIsTooShortError,
                onValueChange = {
                    viewModel.username.value = it
                })
            if (error.value is RegisterViewModel.ErrorState.UsernameIsTooShortError) {
                Text(
                    text = error.value.message,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
        Column(modifier = Modifier.padding(0.dp, 8.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                label = { Text("Password") },
                onValueChange = {
                    viewModel.password.value = it
                },
                isError = error.value is RegisterViewModel.ErrorState.PasswordIsTooShortError,
                visualTransformation = PasswordVisualTransformation()
            )
            if (error.value is RegisterViewModel.ErrorState.PasswordIsTooShortError) {
                Text(
                    text = error.value.message,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
        if (error.value is RegisterViewModel.ErrorState.UsernameOrPasswordAreEmpty
            || error.value is RegisterViewModel.ErrorState.UserExistsError) {
            Text(
                modifier = Modifier.padding(0.dp, 8.dp),
                text = error.value.message,
                color = Color.Red,
                fontSize = 12.sp
            )
        }
        Button(
            modifier = modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.register()
            }
        ) {
            Text("Log in")
        }
    }
}
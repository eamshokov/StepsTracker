package ru.eamshokov.stepstracker.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect

@Composable
fun LoginView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel:LoginViewModel = viewModel(),
){
    val username: String by viewModel.username.collectAsState()
    val password: String by viewModel.password.collectAsState()
    val result = viewModel.loginResult.collectAsState(false)
    val error = viewModel.error.collectAsState()
    if(result.value){
        navController.navigate("activity"){
            launchSingleTop = true
        }
    }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.Center,

    ){
        Column(Modifier.padding(0.dp, 8.dp)){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Name") },
                value = username,
                isError = error.value is LoginViewModel.ErrorState.UsernameError,
                onValueChange = {
                    viewModel.username.value = it
                })
            if(error.value is LoginViewModel.ErrorState.UsernameError){
                Text(
                    text = error.value.message,
                    color = Color.Red,
                    fontSize = 12.sp)
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
                isError = error.value is LoginViewModel.ErrorState.PasswordError,
                visualTransformation = PasswordVisualTransformation()
            )
            if(error.value is LoginViewModel.ErrorState.PasswordError){
                Text(
                    text = error.value.message,
                    color = Color.Red,
                    fontSize = 12.sp)
            }
        }

        Button(
            modifier = modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.login()
            }
        ) {
            Text("Log in")
        }
        TextButton(
            modifier = modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth(),
            onClick = {
                navController.navigate("register")
            }
        ) {
            Text("Register")
        }
    }
}
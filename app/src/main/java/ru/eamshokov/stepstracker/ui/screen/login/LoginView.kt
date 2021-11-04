package ru.eamshokov.stepstracker.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun LoginView(
    navController: NavController,
    viewModel:LoginViewModel = viewModel(),
    modifier:Modifier = Modifier()
){
    Text("login")
}
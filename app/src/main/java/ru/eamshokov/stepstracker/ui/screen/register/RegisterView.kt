package ru.eamshokov.stepstracker.ui.screen.register

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun RegisterView(
    navController: NavController,
    viewModel:RegisterViewModel = viewModel(),
    modifier: Modifier = Modifier()){
    Text("register")
}
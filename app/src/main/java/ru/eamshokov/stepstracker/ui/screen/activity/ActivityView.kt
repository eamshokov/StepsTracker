package ru.eamshokov.stepstracker.ui.screen.activity

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun ActivityView(
    navController: NavController,
    vm:ActivityViewModel = viewModel(),
    modifier: Modifier = Modifier()){
    Text("activity")
}
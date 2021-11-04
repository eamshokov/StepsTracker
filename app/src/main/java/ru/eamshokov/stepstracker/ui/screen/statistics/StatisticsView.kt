package ru.eamshokov.stepstracker.ui.screen.statistics

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun StatisticsView(
    navController: NavController,
    viewModel: StatisticsViewModel = viewModel(),
    modifier: Modifier = Modifier()){
    Text("statistics")
}
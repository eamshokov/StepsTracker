package ru.eamshokov.stepstracker.ui.screen.statistics

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun StatisticsView(
    navController: NavController,
    viewModel: StatisticsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier()){
    Text("statistics")
}
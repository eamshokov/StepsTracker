package ru.eamshokov.stepstracker.ui.screen.activity

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun ActivityView(navController: NavController, modifier: Modifier = Modifier()){
    Text("activity")
}
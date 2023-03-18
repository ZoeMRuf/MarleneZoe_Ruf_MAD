package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.screens.DetailScreen
import com.example.movieapp.screens.HomeScreen

@Composable
fun Navigation (){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){ HomeScreen(navController = navController)}
        composable("details"){ DetailScreen(/* navController = navController */)}
    }
}
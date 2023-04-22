package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.models.Screen
import com.example.movieapp.screens.AddMovieScreen
import com.example.movieapp.screens.DetailScreen
import com.example.movieapp.screens.FavoritesScreen
import com.example.movieapp.screens.HomeScreen

@Composable
fun Navigation (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(
                navController = navController
            )
        }

        composable(
            route= Screen.Detail.route +"/{movieId}",
            arguments = listOf(navArgument("movieId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getInt("movieId")  //getString("movieId")
            )
        }

        composable(
            route = Screen.Favorites.route
        ){
            FavoritesScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.AddMovie.route
        ){
            AddMovieScreen(
                navController = navController,
            )
        }

        //Screen.Home.route = "home_screen"
        //Screen.Detail.route = "detail_screen"
        //Screen.Favorites.route = "favorites_screen"
        //Screen.AddMovie.route = "addMovie_screen"
    }
}
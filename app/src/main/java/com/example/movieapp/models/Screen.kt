package com.example.movieapp.models

sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Detail: Screen(route = "detail_screen")
    object Favorites: Screen(route = "favorites_screen")
    object AddMovie: Screen(route = "addMovie_screen")
}



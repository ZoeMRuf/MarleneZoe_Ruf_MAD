package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.composables.MovieList
import com.example.movieapp.viewModels.MovieViewModel

@Composable
fun HomeScreen(navController: NavController, movieViewModel: MovieViewModel) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        MovieList(navController, movieViewModel)
    }
}

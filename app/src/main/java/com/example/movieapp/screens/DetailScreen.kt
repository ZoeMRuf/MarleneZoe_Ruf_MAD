package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.composables.ImageRow
import com.example.movieapp.composables.MovieRow
import com.example.movieapp.composables.SimpleAppBar
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

@Composable
fun DetailScreen(navController: NavController, movieId: String?){
    // Get a Movie out of List of Movies by its id
    val movie: Movie = getMovies()[getMovies().indexOfFirst { it.id == movieId }]

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            SimpleAppBar(title = movie.title, navController = navController)
            MovieRow(movie = movie){ /* On Click = Nothing */ }
            ImageRow(images = movie.images, title = "Movie Images" )
        }
    }
}

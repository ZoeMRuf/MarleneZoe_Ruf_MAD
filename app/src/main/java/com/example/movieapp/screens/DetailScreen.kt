package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.ImageRow
import com.example.movieapp.MovieRow
import com.example.movieapp.SimpleAppBar
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
        //Not Scrollable !!! -> LazyColum ???
        Column(Modifier.fillMaxWidth()) {
            SimpleAppBar(title = movie.title, navController = navController)
            MovieRow(movie = movie){ /* On Click = Nothing */ }
            ImageRow(images = movie.images, title = "Movie Images" )
        }
    }
}

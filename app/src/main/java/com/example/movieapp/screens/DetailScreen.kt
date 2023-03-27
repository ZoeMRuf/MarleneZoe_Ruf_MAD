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
import com.example.movieapp.models.Screen
import com.example.movieapp.models.getMovies
import com.example.movieapp.viewModels.MovieViewModel

@Composable
fun DetailScreen(navController: NavController, movieViewModel: MovieViewModel, movieId: String?){
    // Get a Movie out of List of Movies by its id
    //val movie: Movie = movieViewModel.movieList.filter { it.id == movieId}[0]
    val movie: Movie = movieViewModel.getMovieById(movieId)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        Column(Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
            SimpleAppBar(title = movie.title, navController = navController)
            MovieRow(
                movie = movie,
                onFavoriteClick = {
                    movieViewModel.toggleIsFavorite(movie)
                }
            )
            ImageRow(images = movie.images, title = "Movie Images" )
        }
    }
}

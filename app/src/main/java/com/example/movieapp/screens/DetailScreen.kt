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
import com.example.movieapp.viewModels.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavController, movieViewModel: MovieViewModel, movieId: Int?){
    val coroutineScope = rememberCoroutineScope()
    val movie: Movie = movieViewModel.getMovieById(movieId) //TODO: Fix coroutine Problem
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
                favorite = movie.isFavorite,
                onFavoriteClick = {
                    coroutineScope.launch {
                        movieViewModel.toggleIsFavorite(it)
                    }
                }
            )
            movie.images?.let { ImageRow(images = it, title = "Movie Images" ) }
            /* TODO: Not perfect because if image is null no movie row shown
                    ImageRow(images = movie.images, title = "Movie Images" )
            */
        }
    }
}

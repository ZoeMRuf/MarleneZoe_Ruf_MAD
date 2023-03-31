package com.example.movieapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.composables.MovieRow
import com.example.movieapp.composables.SimpleAppBar
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Screen
import com.example.movieapp.viewModels.MovieViewModel

@Composable
fun FavoritesScreen(navController: NavController, movieViewModel: MovieViewModel){
    val favoriteMovies: List<Movie> = movieViewModel.getFavoriteMovies()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxWidth()) {
            SimpleAppBar(title = "Favorites", navController = navController)
            LazyColumn (userScrollEnabled = true) {
                items(favoriteMovies) { movie ->
                    MovieRow(
                        movie = movie,
                        onMovieRowClick = { movieId ->
                            navController.navigate(Screen.Detail.route + "/$movieId")},
                        onFavoriteClick = {
                            movieViewModel.toggleIsFavorite(it)
                        }
                    )
                }
            }
        }
    }
}
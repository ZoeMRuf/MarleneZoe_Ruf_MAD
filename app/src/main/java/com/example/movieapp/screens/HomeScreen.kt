package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.composables.HomeAppBar
import com.example.movieapp.composables.MovieRow
import com.example.movieapp.models.Screen
import com.example.movieapp.viewModels.MovieViewModel

@Composable
fun HomeScreen(navController: NavController, movieViewModel: MovieViewModel) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxWidth()) {
            HomeAppBar(
                onDropDownEdit = { navController.navigate(Screen.AddMovie.route) },
                onDropDownFavorite = { navController.navigate(Screen.Favorites.route) }
            )
            LazyColumn (userScrollEnabled = true) {
                items(movieViewModel.movieList) { movie ->
                    MovieRow(
                        movie = movie,
                        onMovieRowClick = { movieId ->
                            navController.navigate(Screen.Detail.route + "/$movieId") },
                        onFavoriteClick = { movieViewModel.toggleIsFavorite(it) }
                    )
                }
            }
        }
    }
}

package com.example.movieapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.composables.MovieRow
import com.example.movieapp.composables.SimpleAppBar
import com.example.movieapp.models.Screen
import com.example.movieapp.utils.InjectorUtils
import com.example.movieapp.viewModels.FavoriteViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(navController: NavController){
    val coroutineScope = rememberCoroutineScope()
    val favoriteViewModel: FavoriteViewModel =
        viewModel(factory = InjectorUtils.provideMovieViewModelFactory(LocalContext.current))
    val favoriteMovieList by favoriteViewModel.movie.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxWidth()) {
            SimpleAppBar(title = "Favorites", navController = navController)
            LazyColumn (userScrollEnabled = true) {
                items(favoriteMovieList) { movie ->
                    MovieRow(
                        movie = movie,
                        favorite = movie.isFavorite,
                        onMovieRowClick = { movieId ->
                            navController.navigate(Screen.Detail.route + "/$movieId")},
                        onFavoriteClick = {
                            coroutineScope.launch {
                                favoriteViewModel.toggleIsFavorite(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

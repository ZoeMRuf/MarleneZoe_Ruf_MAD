package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.composables.HomeAppBar
import com.example.movieapp.composables.MovieRow
import com.example.movieapp.models.Screen
import com.example.movieapp.utils.InjectorUtils
import com.example.movieapp.viewModels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel =
        viewModel(factory= InjectorUtils.provideMovieViewModelFactory(LocalContext.current))

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
                items(homeViewModel.movie.value) { movie ->
                    MovieRow(
                        movie = movie,
                        favorite = movie.isFavorite,
                        onMovieRowClick = { movieId ->
                            navController.navigate(Screen.Detail.route + "/$movieId") },
                        onFavoriteClick = {
                            coroutineScope.launch {
                                homeViewModel.toggleIsFavorite(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

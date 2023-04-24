package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.composables.ImageRow
import com.example.movieapp.composables.MovieRow
import com.example.movieapp.composables.SimpleAppBar
import com.example.movieapp.models.getMovies
import com.example.movieapp.utils.InjectorUtils
import com.example.movieapp.viewModels.DetailViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavController, movieId: Int){
    val detailViewModel: DetailViewModel =
        viewModel(factory= InjectorUtils.provideMovieViewModelFactory(LocalContext.current))
    val coroutineScope = rememberCoroutineScope()

    var detailMovie by remember {
        mutableStateOf(getMovies()[0])
    }

    LaunchedEffect(key1 = true){
        detailMovie = detailViewModel.getMovieById(movieId)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            SimpleAppBar(title = detailMovie.title, navController = navController)
            MovieRow(
                movie = detailMovie,
                onFavoriteClick = {
                    coroutineScope.launch {
                        detailViewModel.toggleIsFavorite(detailMovie)
                        detailMovie = detailViewModel.getMovieById(movieId) //BugFix for Favorite-Button
                    }
                }
            )
            detailMovie.images?.let { ImageRow(images = it, title = "Movie Images" ) }
            /* TODO: Not perfect because if image is null no movie row shown
                    ImageRow(images = movie.images, title = "Movie Images" )
            */
        }
    }
}

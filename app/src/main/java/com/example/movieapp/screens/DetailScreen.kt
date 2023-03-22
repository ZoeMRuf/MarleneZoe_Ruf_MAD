package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.models.Movie


@Composable
fun DetailScreen(navController: NavController, movieId: String?){

    movieId?.let {
        Text(text = "Hello Detail screen $movieId")


    /*val movie = getMovies()[0];
    Column(Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text(text = "Movies Title") },
            navigationIcon = {
                Icon(
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .size(30.dp),
                    contentDescription = "Back to Home_Screen",
                    imageVector = Icons.Default.ArrowBack
                )
            }
        )
        MovieRow(movie = movie)
        MoviePosters(movie = movie)

     */

    }
}

// Does not WORK
@Composable
fun MoviePosters(movie: Movie){
    LazyRow(userScrollEnabled = true){
        items(movie.images){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.images) // Why do we get a list of urls form getMovies() ???
                    .crossfade(true) // Not sure what this line does ???
                    .build(),
                placeholder = painterResource(R.drawable.wakanda_img),
                contentDescription =  "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
} 

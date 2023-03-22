package com.example.movieapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

@Composable
fun DetailScreen(navController: NavController, movieId: String?){
    // Get a Movie out of List of Movies by its id
    val movie: Movie = getMovies()[getMovies().indexOfFirst { it.id == movieId }]

    //Not Scrollable !!! -> LazyColum ???
    Column(Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text(text = movie.title) },
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
        MovieRow(movie = movie){ /* On Click = Nothing */ }
        ImageRow(images = movie.images, title = "Movie Images" )
    }
}

@Composable
fun ImageRow(images: List<String>, title: String){
    Spacer(Modifier.size(10.dp))
    Divider(thickness = 1.5.dp, color = Color.LightGray)
    Text(text = title,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
    LazyRow(Modifier.fillMaxWidth()){
        items(images) {
            Card(
                Modifier
                    .width(250.dp) //???
                    .height(300.dp) //???
                    .padding(5.dp),
                shape = RoundedCornerShape(3.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.wakanda_img),
                    contentDescription =  "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
} 

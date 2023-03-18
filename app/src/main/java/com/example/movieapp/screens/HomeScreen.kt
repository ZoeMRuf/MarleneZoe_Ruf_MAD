package com.example.movieapp.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun HomeScreen(navController: NavController) {
    MovieList()
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {/*default = do nothing*/}){
    val padding = 10.dp
    val roundCorner = 15.dp
    var iconArrow = Icons.Default.KeyboardArrowUp
    var iconFavorite = Icons.Default.FavoriteBorder
    var clickArrow by remember { mutableStateOf(false) }
    var clickFavorite by remember { mutableStateOf(false) }

    // If conditions to change icons when clicked, clickArrow also shows/hides AnimatedVisibility
    if (clickArrow){ iconArrow = Icons.Default.KeyboardArrowDown }
    if (clickFavorite){ iconFavorite = Icons.Default.Favorite }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(padding)
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(roundCorner),
        elevation = 5.dp
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.images[0]) // Why do we get a list of urls form getMovies() ???
                        .crossfade(true) // Not sure what this line does ???
                        .build(),
                    placeholder = painterResource(R.drawable.wakanda_img),
                    contentDescription =  "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(roundCorner, roundCorner))
                )
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        contentDescription = "Add to favorites",
                        imageVector = iconFavorite,
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .clickable { clickFavorite = !clickFavorite }
                            .size(35.dp)
                    )
                }
            }
            Spacer(Modifier.size(padding))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(padding),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.h6)
                Icon(
                    modifier = Modifier
                        .clickable { clickArrow = !clickArrow }
                        .size(35.dp),
                    contentDescription = "Show details",
                    imageVector = iconArrow
                )
            }
            AnimatedVisibility(visible = clickArrow) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(padding)) {
                    Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.body1)
                    Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.body1)
                    Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.body1)
                    Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.body1)
                    Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.body1)
                    Spacer(Modifier.size(padding))
                    Divider(thickness = 1.5.dp, color = Color.LightGray)
                    Spacer(Modifier.size(padding))
                    Text(text = "Plot: ${movie.plot}", Modifier.padding(padding),
                        style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}

@Composable
fun MovieList(movies: List<Movie> = getMovies()){
    val dropDownIcon = Icons.Default.MoreVert
    var expanded by remember {
        mutableStateOf(false)
    }
    Column(Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text(text = "Movies") },
            actions = {
                Icon(
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .size(35.dp),
                    contentDescription = "More Options",
                    imageVector = dropDownIcon
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false}) {
                    DropdownMenuItem(onClick = {
                        // -> When "Favourites" is Clicked
                    }) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            contentDescription = "Favorites",
                            imageVector = Icons.Default.Favorite
                        )
                        Spacer(Modifier.size(10.dp))
                        Text(text = "Favorites")
                    }
                }
            }
        )
        LazyColumn () {
            items(movies) { movies ->
                MovieRow(movies){ movieID ->
                    Log.d("MovieRow", "Item was clicked: $movieID")
                }
            }
        }
    }
}
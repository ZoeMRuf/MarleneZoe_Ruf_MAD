package com.example.movieapp.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.Screen
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

@Composable
fun SimpleAppBar(title: String, navController: NavController){
    TopAppBar(
        title = { Text(text = title) },
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
}

@Composable
fun MovieImage(data: String, description: String){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.wakanda_img),
        contentDescription =  description,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
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
                MovieImage(data = it, description = "Image in ImageRow")
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {/*default = do nothing*/}){
    val padding = 10.dp
    val roundCorner = 10.dp
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
                MovieImage(data = movie.images[0], description = "Movie Poster")
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
fun MovieList(navController: NavController = rememberNavController(), movies: List<Movie> = getMovies()){
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
                        navController.navigate(Screen.Favorites.route)
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
        LazyColumn (userScrollEnabled = true) {
            items(movies) { movies ->
                MovieRow(movies){ movieId ->
                    navController.navigate(Screen.Detail.route + "/$movieId")
                }
            }
        }
    }
}
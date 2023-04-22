package com.example.movieapp.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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


@Composable
fun ShowErrorMessage(msg: String, visible: Boolean){
    AnimatedVisibility(visible = visible) {
        Text(
            text = "$msg is required. Please try again.",
            color = Color.Red,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
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
fun MovieImage(data: String?, description: String){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.no_image_found),
        error = painterResource(R.drawable.no_image_found), /*if img request is unsuccessful*/
        fallback = painterResource(R.drawable.no_image_found), /*if data is null*/
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
                    .width(400.dp) //???
                    .height(250.dp) //???
                    .padding(5.dp),
                shape = RoundedCornerShape(3.dp)
            ) {
                MovieImage(data = it, description = "Image in ImageRow")
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onFavoriteClick: (movie: Movie) -> Unit = {}, onMovieRowClick: (Int?) -> Unit = {}){
    val padding = 10.dp
    var clickArrowIcon by remember { mutableStateOf(false) } // State-Holder for show/hide Details

    Card(
        Modifier
            .fillMaxWidth()
            .padding(padding)
            .clickable { onMovieRowClick(movie.id) },
        shape = RoundedCornerShape(10.dp),
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
                MovieImage(data = movie.images?.get(0), description = "Movie Poster")
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector =
                        if (movie.isFavorite){
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Add to Favorites",
                        modifier = Modifier.size(35.dp)
                            .clickable {
                                onFavoriteClick(movie)
                            }
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
                    modifier = Modifier.size(35.dp)
                        .clickable {
                            clickArrowIcon = !clickArrowIcon
                        },
                    contentDescription = "Show Details",
                    imageVector =
                    if (clickArrowIcon){
                        Icons.Default.KeyboardArrowDown
                    } else {
                        Icons.Default.KeyboardArrowUp
                    }
                )
            }
            AnimatedVisibility(visible = clickArrowIcon){
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(padding)) {
                    Text(style = MaterialTheme.typography.body1,
                        text = "Director: ${movie.director} \n" +
                                "Released: ${movie.year} \n" +
                                "Genre: ${movie.genre} \n" +
                                "Actors: ${movie.actors} \n" +
                                "Rating: ${movie.rating}")
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
fun HomeAppBar( onDropDownEdit: () -> Unit, onDropDownFavorite: () -> Unit ){
    var expandedMenu by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Movies") },
        actions = {
            Icon(
                modifier = Modifier
                    .clickable { expandedMenu = !expandedMenu }
                    .size(35.dp),
                contentDescription = "More Options",
                imageVector = Icons.Default.MoreVert
            )
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false}) {
                DropdownMenuItem(onClick = {
                    onDropDownEdit()
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Add Movie",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(text = "Add Movie",
                            modifier = Modifier
                                .width(100.dp)
                                .padding(4.dp)
                        )
                    }
                }
                DropdownMenuItem(onClick = {
                    onDropDownFavorite()
                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites",
                            modifier = Modifier.padding(4.dp),
                        )
                        //Spacer(Modifier.size(10.dp))
                        Text(text = "Favorites",
                            modifier = Modifier
                                .width(100.dp)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    )
}
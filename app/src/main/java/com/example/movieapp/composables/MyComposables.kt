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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun ToggleIcon(icon: ImageVector,
               toggleIcon: ImageVector,
               tint: Color = Color.Black,
               contentDescription: String = "Icon",
               onIconClick: () -> Unit = {}){
    var showIcon = icon
    var clickIcon by remember { mutableStateOf(false) }
    if (clickIcon){ showIcon = toggleIcon }
    Icon(
        modifier = Modifier
            .clickable {
                clickIcon = !clickIcon
                onIconClick()
            }
            .size(35.dp),
        contentDescription = contentDescription,
        tint = tint,
        imageVector = showIcon
    )
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {/*default = do nothing*/}){
    val padding = 10.dp
    //State-Holders to show/hide AnimatedVisibility
    var showDetails by remember { mutableStateOf(false) }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(padding)
            .clickable { onItemClick(movie.id) },
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
                MovieImage(data = movie.images[0], description = "Movie Poster")
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.TopEnd
                ) {
                    ToggleIcon(
                        icon = Icons.Default.FavoriteBorder,
                        toggleIcon = Icons.Default.Favorite,
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = "Add to Favorites"){}
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
                ToggleIcon(
                    icon = Icons.Default.KeyboardArrowUp,
                    toggleIcon = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Show Details"){
                    showDetails = !showDetails
                }
            }
            AnimatedVisibility(visible = showDetails){
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
fun MovieList(navController: NavController = rememberNavController(), movies: List<Movie> = getMovies()){
    var expandedMenu by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth()) {
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
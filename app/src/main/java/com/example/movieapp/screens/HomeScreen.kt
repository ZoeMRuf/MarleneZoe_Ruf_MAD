package com.example.movieapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                            navController.navigate(Screen.AddMovie.route) }) {
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
                            navController.navigate(Screen.Favorites.route) }) {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorites",
                                    modifier = Modifier.padding(4.dp),
                                )
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
            LazyColumn (userScrollEnabled = true) {
                items(movieViewModel.movieList) { movie ->
                    MovieRow(
                        movie = movie,
                        onMovieRowClick = { movieId ->
                            navController.navigate(Screen.Detail.route + "/$movieId")},
                        onFavoriteClick = { movieViewModel.toggleIsFavorite(movie) }
                    )
                }
            }
        }
    }
}

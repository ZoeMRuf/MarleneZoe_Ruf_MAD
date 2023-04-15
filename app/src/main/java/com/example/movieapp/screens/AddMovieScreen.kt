package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.models.Genre
import com.example.movieapp.R
import com.example.movieapp.composables.ShowErrorMessage
import com.example.movieapp.composables.SimpleAppBar
import com.example.movieapp.models.ListItemSelectable
import com.example.movieapp.models.Movie
import com.example.movieapp.viewModels.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun AddMovieScreen(navController: NavController, movieViewModel: MovieViewModel){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleAppBar(
                title = stringResource(id = R.string.add_movie),
                navController = navController)
        },
    ) { padding ->
        MainContent(
            Modifier.padding(padding),
            onAddButtonClick = {addMovie ->
                coroutineScope.launch {
                    movieViewModel.addMovie(addMovie)
                } },
            InputValidationCheck = {Input -> movieViewModel.validationUserInput(Input)} // No Coroutine because no Database access
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onAddButtonClick: (addMovie: Movie) -> Unit = {},
    InputValidationCheck: (input: String) -> Boolean
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            var title by remember { mutableStateOf("") }
            var titleError by remember { mutableStateOf(false) }

            var year by remember { mutableStateOf("") }
            var yearError by remember { mutableStateOf(false) }

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }

            var director by remember { mutableStateOf("") }
            var directorError by remember { mutableStateOf(false) }

            var actors by remember { mutableStateOf("") }
            var actorError by remember { mutableStateOf(false) }

            var plot by remember { mutableStateOf("") }
            var plotError by remember { mutableStateOf(false) }

            var rating by remember { mutableStateOf("") }
            var ratingError by remember { mutableStateOf(false) }

            var isEnabledSaveButton by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    titleError = InputValidationCheck(it)
                    title = it
                    isEnabledSaveButton = enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = titleError
            )
            ShowErrorMessage(msg = "Title", visible = titleError)


            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    yearError = InputValidationCheck(it)
                    year = it
                    isEnabledSaveButton = enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = yearError
            )
            ShowErrorMessage(msg = "Year", visible = yearError)

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6)

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)){
                items(genreItems) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            genreItems = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    directorError = InputValidationCheck(it)
                    director = it
                    isEnabledSaveButton = enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = directorError
            )
            ShowErrorMessage(msg = "Director", visible = directorError)

            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    actorError = InputValidationCheck(it)
                    actors = it
                    isEnabledSaveButton = enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = actorError
            )
            ShowErrorMessage(msg = "Actor", visible = actorError)

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = {
                    plotError = InputValidationCheck(it)
                    plot = it
                    isEnabledSaveButton = enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)) },
                isError = plotError
            )
            ShowErrorMessage(msg = "Plot", visible = plotError)

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    ratingError = InputValidationCheck(it)
                    rating = if(it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                    isEnabledSaveButton = enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = ratingError
            )
            ShowErrorMessage(msg = "Rating", visible = ratingError)

            val movieToAdd = Movie(
                //id = "ID01", /* I don't know the ID yet so I used a MockUp*/
                title = title,
                year = year,
                genre = selectedGenres(genreItems),
                director = director,
                actors = actors,
                plot = plot,
                rating = if (rating.toFloatOrNull() != null && rating.toFloat() < 10.0) rating.toFloat() else 0.0f // Forgot < 10.0 check in LD_04
            )

            Button(
                enabled = isEnabledSaveButton,
                onClick = {
                    onAddButtonClick(movieToAdd)
                }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

fun selectedGenres(genres: List<ListItemSelectable>): List<Genre>{
    val selectedGenres: MutableList<Genre> = mutableListOf()
    genres.forEach {
        if (it.isSelected){
          selectedGenres.add(enumValueOf(it.title))
        }
    }
    return selectedGenres
}

fun enableButton(titleError:Boolean, yearError:Boolean, directorError:Boolean, actorError:Boolean, plotError:Boolean, ratingError:Boolean ): Boolean{
    if (!titleError && !yearError && !directorError && !actorError && !plotError && !ratingError){
        return true
    }
    return false
}
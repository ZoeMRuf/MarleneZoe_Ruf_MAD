package com.example.movieapp.viewModels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
class MovieViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    /*TODO: Implement the Logic
    -> isFavorite function that sets isFavorite to true
    -> Toggle the isFavorite state of a movie

    -> Get a list of all favorite movies
    -> Get a list of all movies
     */
}
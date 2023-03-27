package com.example.movieapp.viewModels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

class MovieViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    //logic
}
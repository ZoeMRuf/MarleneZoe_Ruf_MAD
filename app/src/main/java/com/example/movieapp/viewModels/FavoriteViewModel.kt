package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie

import com.example.movieapp.repositories.MovieRepository

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movie = MutableStateFlow(listOf<Movie>())
    val movie: StateFlow<List<Movie>> = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect{value ->
                _movie.value = value
            }
        }
    }

    suspend fun toggleIsFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}




package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movie = MutableStateFlow(listOf<Movie>()) //
    val movie: StateFlow<List<Movie>> = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            //getMovies().forEach { movie ->  repository.add(movie) } // -> executed just Once to load movies into Database
            repository.getAllMovies().collect{ value ->
                if (!value.isNullOrEmpty()){
                    _movie.value = value
                }
            }
        }
    }

    suspend fun toggleIsFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}




package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movie = MutableStateFlow(listOf<Movie>())
    val movie: StateFlow<List<Movie>> = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect{ movieList ->
                if (!movieList.isNullOrEmpty()){
                    _movie.value = movieList
                }
            }
        }
    }

    fun getMovieById(movieId: Int?): Movie {
        if (movieId !== null){
            return repository.getMovieById(movieId)
        }
        throw java.lang.IllegalArgumentException("Movie needs to have an ID")
    }


    suspend fun toggleIsFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}

package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.*

class DetailViewModel(private val repository: MovieRepository): ViewModel() {

    lateinit var movie: Flow<Movie>

    suspend fun getMovieById(movieId: Int): Movie {
        return repository.getMovieById(movieId)
    }
    
    suspend fun toggleIsFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }
}

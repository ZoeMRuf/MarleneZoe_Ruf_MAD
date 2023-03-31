package com.example.movieapp.viewModels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
class MovieViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    fun getMovieById(movieId: String?): Movie {
        return _movieList.filter { it.id == movieId}[0]
    }

    fun getFavoriteMovies(): List<Movie> {
        return _movieList.filter { it.isFavorite }
    }

    fun toggleIsFavorite(movie: Movie){
        _movieList.find { it.id == movie.id }?.let { movie.isFavorite = !movie.isFavorite }
    }
}
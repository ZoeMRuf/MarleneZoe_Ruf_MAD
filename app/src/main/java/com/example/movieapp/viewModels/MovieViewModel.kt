package com.example.movieapp.viewModels

import android.util.Log
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

    fun toggleIsFavorite(movie: Movie){
        _movieList.find { it.id == movie.id }?.let { movie -> movie.isFavorite = !movie.isFavorite }
        Log.i("Message", "isFavorite = ${movie.isFavorite}")
    }

    fun getFavoriteMovies(): List<Movie> {
        return _movieList.filter { it.isFavorite }
    }

    /* TODO: Implement the Logic
    -> isFavorite function that sets isFavorite to true
    -> Toggle the isFavorite state of a movie

    -> Get a list of all favorite movies
    -> Get a list of all movies
     */
}
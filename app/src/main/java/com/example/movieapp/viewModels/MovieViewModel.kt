package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository): ViewModel() {
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
        if (movieId !== null){ return repository.getMovieById(movieId) }
        throw java.lang.IllegalArgumentException("Movie needs to have an ID")
    }

    fun getFavoriteMovies(): List<Movie> {
        return repository.getFavoriteMovies() // TODO: FIX Favourite Screen

    }

    suspend fun toggleIsFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }

    suspend fun addMovie(movie: Movie){
        repository.add(movie)
    }

    fun validationUserInput(input: String): Boolean{
        return input.isEmpty()
    }


}

/* Old ViewModel Functions:
private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    fun getMovieById(movieId: Int?): Movie {
        return _movieList.filter { it.id == movieId}[0]
    }

    fun getFavoriteMovies(): List<Movie> {
        return _movieList.filter { it.isFavorite }
    }

    fun toggleIsFavorite(movie: Movie){
        _movieList.find { it.id == movie.id }?.let { movie.isFavorite = !movie.isFavorite }
    }

    fun addMovie(movie: Movie){
        _movieList.add(movie)
    }

    fun validationUserInput(input: String): Boolean{
            return input.isEmpty()
    }
 */



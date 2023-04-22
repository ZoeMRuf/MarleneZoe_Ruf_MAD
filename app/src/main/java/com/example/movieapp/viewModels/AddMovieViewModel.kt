package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository

class AddMovieViewModel(private val repository: MovieRepository): ViewModel() {

    suspend fun addMovie(movie: Movie){
        repository.add(movie)
    }

    fun validationUserInput(input: String): Boolean{
        return input.isEmpty()
    }
}




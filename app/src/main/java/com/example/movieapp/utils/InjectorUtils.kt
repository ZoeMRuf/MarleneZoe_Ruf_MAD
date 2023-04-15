package com.example.movieapp.utils

import android.content.Context
import com.example.movieapp.data.MovieDatabase
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewModels.MovieViewModelFactory

object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory {
        val repository = getMovieRepository(context)
        return MovieViewModelFactory(repository)
    }
}
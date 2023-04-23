package com.example.movieapp.repositories

import com.example.movieapp.data.MovieDao
import com.example.movieapp.models.Movie

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun add(movie: Movie) = movieDao.add(movie)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    fun getAllMovies() = movieDao.readAll()

    fun getFavoriteMovies() = movieDao.readFavorite()

    suspend fun getMovieById(id: Int) = movieDao.getMovieById(id)

    fun deleteAll() = movieDao.deleteAll()
}
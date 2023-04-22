package com.example.movieapp.data

import androidx.room.*
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun add(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun readAll(): Flow<List<Movie>>

    @Query("SELECT * from movie where isFavorite = 1")
    fun readFavorite(): Flow<List<Movie>>

    @Query("Select * from movie where id = :movieId")
    fun getMovieById(movieId: Int): Movie

    @Query("DELETE from movie")
    fun deleteAll()
}
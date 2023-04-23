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

    @Query("SELECT * from movie WHERE isFavorite = 1")  //BIG FAIL: WHERE was in lower-case letters
    fun readFavorite(): Flow<List<Movie>>

    @Query("Select * from movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie

    @Query("DELETE from movie")
    fun deleteAll()
}
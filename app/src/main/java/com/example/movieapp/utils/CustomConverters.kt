package com.example.movieapp.utils

import androidx.room.TypeConverter
import com.example.movieapp.models.Genre

class CustomConverters {

    // for Type Conversion of Genres
    @TypeConverter
    fun fromGenreList(value: List<Genre>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toGenreList(value: String): List<Genre> {
        val genresList = mutableListOf<Genre>()

        value.split(",").map{ it.trim()}.forEach {
            genresList.add(enumValueOf(it))
        }

        return genresList
    }

    // for Type Conversion of Images
    @TypeConverter
    fun formStringList(value: List<String>) = value.joinToString(",")

    @TypeConverter
    fun toStringList(value: String) = value.split(",").map{ it.trim()}
}
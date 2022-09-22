package com.kel4.tubes_4_bioskop.room

import androidx.room.*
import com.kel4.tubes_4_bioskop.entity.Movie

@Dao
interface MovieDao {
    @Insert
    suspend fun addMovie(movie: Movie)
    @Update
    suspend fun updateMovie(movie: Movie)
    @Delete
    suspend fun deleteMovie(movie: Movie)
    @Query("SELECT * FROM movie")
    suspend fun getMovies() : List<Movie>
    @Query("SELECT * FROM movie WHERE judul == 'movie_judul'")
    suspend fun getMovie(user_id: Int) : List<Movie>
}
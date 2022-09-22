package com.kel4.tubes_4_bioskop.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kel4.tubes_4_bioskop.R


@Entity
data class Movie (
    @PrimaryKey(autoGenerate = true)
    val judul: String,
    val rating: Int,
    val sinopsis: String,
    val director: String,
    val writter: String,
    val poster: Int
)
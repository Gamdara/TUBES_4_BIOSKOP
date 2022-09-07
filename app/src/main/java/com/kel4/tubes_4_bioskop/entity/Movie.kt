package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.R

class Movie(var judul: String, var rating: Int ,var sinopsis: String, var director: String, var writter: String, var poster: Int) {
    companion object{
        @JvmField
        var listOfNowPlaying = arrayOf(
            Movie("Ngeri-Ngeri Sedap", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_1),
            Movie("Mencuri Raden Saleh", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_2),
            Movie("Cinta Pertama, Kedua&Ketiga", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_3),
            Movie("Pengabdi Setan", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_4),
            Movie("Filosofi Kopi", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_5),
            Movie("Seperti Dendam", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_6),
            Movie("Yuni", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_7),
            Movie("Teka-Teki Tika", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_8),
            Movie("Gara-Gara Warisan", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_9),
            Movie("KKN", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_10)

        )


        var listOfUpComing = arrayOf(
            Movie("Ngeri-Ngeri Sedap", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_1),
            Movie("Mencuri Raden Saleh", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_2),
            Movie("Cinta Pertama, Kedua&Ketiga", 4, "sinopsis", "director", "writter", R.drawable.poster_nowplaying_3),

        )
    }
}
package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.R

class NowPlaying(var judul:String, var sinopsis: String, var director: String, var writter: String, var poster: Int ) {
    companion object{
        @JvmField
        var listOfNowPlaying = arrayOf(
            NowPlaying("Ngeri-Ngeri Sedap", "sinopsis", "director", "writter", R.drawable.poster_nowplaying_1),
            NowPlaying("Mencuri Raden Saleh", "sinopsis", "director", "writter", R.drawable.poster_nowplaying_2),
            NowPlaying("Cinta Pertama, Kedua&Ketiga", "sinopsis", "director", "writter", R.drawable.poster_nowplaying_3)
        )
    }

}
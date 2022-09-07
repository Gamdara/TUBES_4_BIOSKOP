package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.R

class Upcoming(var judul:String, var sinopsis: String, var director: String, var writter: String, var poster: Int ) {
    companion object{
        @JvmField
        var listOfUpcoming = arrayOf(
            NowPlaying("Black Adam", "sinopsis", "director", "writter", R.drawable.poster_upcoming_1),
            NowPlaying("One Piece Red", "sinopsis", "director", "writter", R.drawable.poster_upcoming_2),
            NowPlaying("Pinocchio", "sinopsis", "director", "writter", R.drawable.poster_upcoming_3)
        )
    }

}
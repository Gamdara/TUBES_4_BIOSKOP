package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.entity.MovieList.listOfNowPlaying

class Ticket (var movie: Movie, var seat: String, var time: String) {
    companion object{
        @JvmField
        var listOfTicket= arrayOf(
            Ticket(MovieList.listOfNowPlaying[0],"2D","18:30"),
            Ticket(MovieList.listOfNowPlaying[1],"2D","18:30")
        )
    }
}
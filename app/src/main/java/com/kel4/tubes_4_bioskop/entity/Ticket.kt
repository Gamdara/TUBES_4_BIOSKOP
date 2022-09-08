package com.kel4.tubes_4_bioskop.entity

class Ticket (var movie: Movie, var seat: String, var time: String) {
    companion object{
        @JvmField
        var listOfTicket= arrayOf(
            Ticket(Movie.listOfNowPlaying[0],"2D","18:30"),
            Ticket(Movie.listOfNowPlaying[0],"2D","18:30")
        )
    }
}
package com.kel4.tubes_4_bioskop.entity

import com.kel4.tubes_4_bioskop.entity.MovieList.listOfNowPlaying

class Ticket (var movie: Movie, var seat: String, var time: String) {
    companion object{
        @JvmField
        var listOfTicket= arrayOf(
            Ticket(MovieList.listOfNowPlaying[0],"2D","18:30"),
            Ticket(MovieList.listOfNowPlaying[1],"3D","18:30"),
            Ticket(MovieList.listOfNowPlaying[2],"4D","18:30")
        )

        fun remove(arr: Array<Ticket>, index: Int) {

            val result = arr.toMutableList()
            result.removeAt(index)
            listOfTicket = result.toTypedArray()
        }
    }




}
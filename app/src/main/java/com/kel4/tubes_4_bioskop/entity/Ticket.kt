package com.kel4.tubes_4_bioskop.entity

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.entity.MovieList.listOfNowPlaying

@Entity
class Ticket (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var movie: Int, var seat: String, var time: String
    ) {
    companion object{
        @JvmField
        var listOfTicket= arrayOf(
            Ticket(98, 0,"2D","18:30"),
            Ticket(97,1,"3D","18:30"),
            Ticket(96,2,"4D","18:30")
        )

        fun remove(arr: Array<Ticket>, index: Int) {
            val result = arr.toMutableList()
            result.removeAt(index)
            listOfTicket = result.toTypedArray()
        }

        fun add(movie: Int) {
            val result = listOfTicket.toMutableList()
            val add = Ticket(50, movie,"2D","18:30")
            result+=add
            listOfTicket = result.toTypedArray()
        }

        fun edit(new : Ticket, position:Int) {
            listOfTicket[position] = new
        }


    }




}
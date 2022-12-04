package com.rama.gdroom_a_10735.room

import androidx.room.*
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.entity.User

@Dao
interface TicketDao {
    @Insert
    suspend fun add(user: Ticket)
    @Update
    suspend fun update(user: Ticket)
    @Delete
    suspend fun delete(user: Ticket)
    @Query("SELECT * FROM Ticket")
    suspend fun getTickets() : List<Ticket>
    @Query("SELECT * FROM Ticket WHERE id =:id")
    suspend fun getTicket(id: Int) : List<Ticket>
}
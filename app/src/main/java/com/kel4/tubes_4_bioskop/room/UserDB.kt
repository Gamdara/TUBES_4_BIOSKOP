package com.rama.gdroom_a_10735.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.entity.User

@Database(
    entities = [User::class, Ticket::class],
    version = 2
)
abstract class UserDB: RoomDatabase() {
    abstract fun noteDao() : UserDao
    abstract fun ticketDao() : TicketDao

    companion object {
        @Volatile private var instance : UserDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDB::class.java,
                "user12345.db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}
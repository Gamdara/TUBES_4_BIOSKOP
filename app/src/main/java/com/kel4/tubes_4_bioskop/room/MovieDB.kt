package com.kel4.tubes_4_bioskop.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.entity.User
import com.rama.gdroom_a_10735.room.UserDao

@Database(
    entities = [User::class],
    version = 1
)
abstract class MovieDB: RoomDatabase() {
    abstract fun noteDao() : UserDao
    companion object {
        @Volatile private var instance : MovieDB? = null
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
                MovieDB::class.java,
                "movie12345.db"
            ).build()
    }
}
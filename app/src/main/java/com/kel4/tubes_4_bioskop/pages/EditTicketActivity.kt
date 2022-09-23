package com.kel4.tubes_4_bioskop.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.rama.gdroom_a_10735.room.Constant
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.android.synthetic.main.activity_edit_ticket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTicketActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private var id: Int = 0
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ticket)
        setupView()
        setupListener()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]
        edit_movie.setText(movie.judul)

        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }
    private fun setupListener() {
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ticketDao().add(
                    Ticket(0,movieId,
                        edit_kursi.text.toString(),
                        edit_time.text.toString(),
                    )
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ticketDao().update(
                    Ticket(id,movieId,
                        edit_kursi.text.toString(),
                        edit_time.text.toString(),
                    )
                )
                finish()
            }
        }
    }
    fun getNote() {
        id = intent.getIntExtra("intent_id", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.ticketDao().getTicket(id)[0]
            edit_kursi.setText(notes.seat)
            edit_time.setText(notes.time)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
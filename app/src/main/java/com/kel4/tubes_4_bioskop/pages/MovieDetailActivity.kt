package com.kel4.tubes_4_bioskop.pages

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.ActivityMainBinding
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.rama.gdroom_a_10735.room.Constant
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.android.synthetic.main.activity_edit_ticket.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {
    private var context: Context? = null
    private var movieId: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]
        tvJudul.setText(movie.judul)
        imageView.setImageResource(movie.poster)
        tvDeskripsi.setText(movie.sinopsis)
        tvDirector.setText(movie.director)
        tvWriter.setText(movie.writter)
    }

    private fun setupListener() {

        btnBuy.setOnClickListener{
            context?.startActivity(
                Intent(context, EditTicketActivity::class.java)
                    .putExtra("intent_id", 0)
                    .putExtra("intent_type", Constant.TYPE_CREATE)
                    .putExtra("movie_id", movieId)
            )
        }
        btnTrailer.setOnClickListener {

        }
    }
}
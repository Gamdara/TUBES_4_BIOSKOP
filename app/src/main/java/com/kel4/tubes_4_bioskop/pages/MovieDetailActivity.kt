package com.kel4.tubes_4_bioskop.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.constant.Constant
import com.kel4.tubes_4_bioskop.databinding.ActivityMovieDetailBinding
import com.kel4.tubes_4_bioskop.entity.MovieList


class MovieDetailActivity : AppCompatActivity() {
    var binding: ActivityMovieDetailBinding? = null
    private var id: Int = 0
    private var movieId: Int = 0
    private var queue: RequestQueue? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(this)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]

        binding!!.tvJudul.setText(movie.judul)
        binding!!.imageView.setImageResource(movie.poster)
        binding!!.tvDeskripsi.setText(movie.sinopsis)
        binding!!.tvDirector.setText(movie.director)
        binding!!.tvWriter.setText(movie.writter)
        binding!!.tvProducer.setText(movie.producer)

        binding!!.btnBuy.setOnClickListener() {
            val mainIntent = Intent(this, EditTicketActivity::class.java)
                .putExtra("intent_id", 0)
                .putExtra("intent_type", Constant.TYPE_CREATE)
                .putExtra("movie_id", movieId)
            this.startActivity(mainIntent)
            finish()
        }


        setContentView(binding?.root)

    }

}
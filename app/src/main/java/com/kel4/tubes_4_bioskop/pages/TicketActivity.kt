package com.kel4.tubes_4_bioskop.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.ActivityTicketBinding
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.entity.MovieList.listOfNowPlaying
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.entity.User
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketActivity : AppCompatActivity() {
    var binding: ActivityTicketBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTicketBinding.inflate(layoutInflater)

        binding!!.btnSubmit.setOnClickListener{
            fun add(arr: Array<Ticket>, movie: Movie, seat: String, time: String): Array<Ticket> {
                val list = arr.toMutableList()
                list.add(Ticket(listOfNowPlaying[3],teKursi.text.toString(),teJam.text.toString()))
                return list.toTypedArray()
                finish()
            }
        }

//        binding!!.btnSubmit.setOnClickListener{
//            Ticket.add(arrayOf(Ticket(listOfNowPlaying[3],"1","1")))
//            finish()
//        }

        setContentView(binding?.root)
    }


}
package com.kel4.tubes_4_bioskop.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kel4.tubes_4_bioskop.databinding.ActivityTicketBinding
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.entity.MovieList.listOfNowPlaying
import com.kel4.tubes_4_bioskop.entity.MovieList.ngeriNgeriSedap
import com.kel4.tubes_4_bioskop.entity.Ticket
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {
    var binding: ActivityTicketBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTicketBinding.inflate(layoutInflater)

        binding!!.btnSubmit.setOnClickListener{
            fun add(arr: Array<Ticket>, movie: Movie, seat: String, time: String): Array<Ticket> {
                val list = arr.toMutableList()
                val indexx = Ticket.index(ngeriNgeriSedap)
                list.add(Ticket(listOfNowPlaying[indexx],teKursi.text.toString(),teJam.text.toString()))
                return list.toTypedArray()
                finish()
            }
        }

//        binding!!.btnSubmit.setOnClickListener{
//            val indexx = Ticket.index(ngeriNgeriSedap)
//            Ticket.add(arrayOf(Ticket(listOfNowPlaying[indexx],teKursi.text.toString(),teJam.text.toString())))
//            finish()
//        }

        setContentView(binding?.root)
    }


}
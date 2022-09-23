package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.RVTicketAdapter
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.pages.ProfileActivity

class TicketFragment : Fragment() {

    lateinit var nav : Menu

   // companion object{
 //       fun edit(arr: Array<Ticket>, index: Int){
//          val mainIntent = Intent(this, TicketActivity(arr, index)::class.java)
//            startActivity(mainIntent)
  //      }

 //   }

    fun refresh(){
        val ft = parentFragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVTicketAdapter = RVTicketAdapter(Ticket.listOfTicket)

        val rvPlaying : RecyclerView = view.findViewById(R.id.rv_ticket)

        rvPlaying.layoutManager = layoutManager

        rvPlaying.setHasFixedSize(true)

        rvPlaying.adapter = adapter
        refresh()
    }

}
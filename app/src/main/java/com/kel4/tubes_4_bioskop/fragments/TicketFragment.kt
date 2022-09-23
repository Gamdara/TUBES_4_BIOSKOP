package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.RVTicketAdapter
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.entity.User
import com.kel4.tubes_4_bioskop.pages.ProfileActivity
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketFragment : Fragment() {

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
        val db by lazy { UserDB(requireContext()) }

        CoroutineScope(Dispatchers.IO).launch {
            val tickets = db.ticketDao().getTickets()
            val adapter : RVTicketAdapter = RVTicketAdapter(tickets.toTypedArray())
            val rvPlaying : RecyclerView = view.findViewById(R.id.rv_ticket)
            rvPlaying.layoutManager = layoutManager
            rvPlaying.setHasFixedSize(true)
            rvPlaying.adapter = adapter
            refresh()
        }

    }


}
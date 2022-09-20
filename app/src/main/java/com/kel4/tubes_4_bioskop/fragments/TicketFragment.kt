package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.RVTicketAdapter
import com.kel4.tubes_4_bioskop.entity.Ticket

class TicketFragment : Fragment() {

    lateinit var nav : Menu

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
    }

}
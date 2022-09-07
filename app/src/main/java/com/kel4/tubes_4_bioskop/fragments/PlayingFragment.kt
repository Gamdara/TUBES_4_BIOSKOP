package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kel4.tubes_4_bioskop.R
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kel4.tubes_4_bioskop.entity.NowPlaying
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kel4.tubes_4_bioskop.RVPlayingAdapter
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.fragments.PlayingFragment
import com.kel4.tubes_4_bioskop.fragments.TicketFragment
import com.kel4.tubes_4_bioskop.fragments.UpcomingFragment

class PlayingFragment : Fragment(){
    lateinit var nav : Menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVPlayingAdapter = RVPlayingAdapter(NowPlaying.listOfNowPlaying)

        val rvPlaying : RecyclerView = view.findViewById(R.id.rv_playing)

        rvPlaying.layoutManager = layoutManager

        rvPlaying.setHasFixedSize(true)

        rvPlaying.adapter = adapter
    }



}
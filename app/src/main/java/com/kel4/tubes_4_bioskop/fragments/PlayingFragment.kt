package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kel4.tubes_4_bioskop.R
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.RVPlayingAdapter
import android.view.Menu
import androidx.recyclerview.widget.GridLayoutManager
import com.kel4.tubes_4_bioskop.entity.MovieList

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
        val layoutManager : GridLayoutManager = GridLayoutManager(context,2)
        val adapter : RVPlayingAdapter = RVPlayingAdapter(MovieList.listOfNowPlaying, this)

        val rvPlaying : RecyclerView = view.findViewById(R.id.rv_playing)

        rvPlaying.layoutManager = layoutManager

        rvPlaying.setHasFixedSize(true)

        rvPlaying.adapter = adapter
    }



}
package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.RVMovieAdapter
import com.kel4.tubes_4_bioskop.entity.Movie

class PlayingFragment : Fragment() {

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
        val adapter : RVMovieAdapter = RVMovieAdapter(Movie.listOfMovie)

        val rvMahasiswa : RecyclerView = view.findViewById(R.id.rv_playing)

        rvMahasiswa.layoutManager = layoutManager

        rvMahasiswa.setHasFixedSize(true)

        rvMahasiswa.adapter = adapter
    }

}
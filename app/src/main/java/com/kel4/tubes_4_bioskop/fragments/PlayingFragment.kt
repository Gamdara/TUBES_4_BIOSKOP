package com.kel4.tubes_4_bioskop.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kel4.tubes_4_bioskop.R
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.RVPlayingAdapter
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.kel4.tubes_4_bioskop.databinding.FragmentPlayingBinding
import com.kel4.tubes_4_bioskop.databinding.FragmentUpcomingBinding
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.pages.MapActivity
import com.kel4.tubes_4_bioskop.pages.ProfileActivity

class PlayingFragment : Fragment(){
    lateinit var nav : Menu
    private var _binding: FragmentPlayingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayingBinding.inflate(inflater, container, false)
        var view : View = binding.root

        val btnProfile = binding.profileButton

        btnProfile.setOnClickListener(){
            val mainIntent = Intent(requireContext(), ProfileActivity::class.java)
            this.startActivity(mainIntent)
        }
        binding.mapsButton.setOnClickListener(){
            val mainIntent = Intent(requireContext(), MapActivity::class.java)
            this.startActivity(mainIntent)
        }

        val btnLogout = binding.logoutButton
        btnLogout.setOnClickListener(){
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure want to exit?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        requireActivity().finishAndRemoveTask()
                    }
                })
                .show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager : GridLayoutManager = GridLayoutManager(context,2)
        val adapter : RVPlayingAdapter = RVPlayingAdapter(MovieList.listOfNowPlaying)

        val rvPlaying : RecyclerView = view.findViewById(R.id.rv_playing)

        rvPlaying.layoutManager = layoutManager

        rvPlaying.setHasFixedSize(true)

        rvPlaying.adapter = adapter
    }



}
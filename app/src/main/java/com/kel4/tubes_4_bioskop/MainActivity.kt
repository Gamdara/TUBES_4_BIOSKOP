package com.kel4.tubes_4_bioskop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kel4.tubes_4_bioskop.fragments.PlayingFragment
import com.kel4.tubes_4_bioskop.fragments.TicketFragment
import com.kel4.tubes_4_bioskop.fragments.UpcomingFragment

class MainActivity : AppCompatActivity() {
    lateinit var nav : Menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        myBottomNavigationView.setOnItemSelectedListener {item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    changeFragment(PlayingFragment())
                }
                R.id.page_2 -> {
                    changeFragment(UpcomingFragment())
                }
                R.id.page_3 -> {
                    changeFragment(TicketFragment())
                }
            }
            false

        }
    }


    fun changeFragment(fragment: Fragment?) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fgc, fragment)
                .commit()
        }
    }




}
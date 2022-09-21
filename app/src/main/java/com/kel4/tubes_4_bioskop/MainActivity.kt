package com.kel4.tubes_4_bioskop

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.fragments.PlayingFragment
import com.kel4.tubes_4_bioskop.fragments.TicketFragment
import com.kel4.tubes_4_bioskop.fragments.UpcomingFragment
import com.kel4.tubes_4_bioskop.pages.AuthActivity
import com.kel4.tubes_4_bioskop.pages.ProfileActivity

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
            true

        }
        val btnProfile = findViewById<ImageView>(R.id.profileButton)
        btnProfile.setOnClickListener(){
            val mainIntent = Intent(this, ProfileActivity::class.java)
            this.startActivity(mainIntent)
        }
        val btnLogout = findViewById<ImageView>(R.id.logoutButton)
        btnLogout.setOnClickListener(){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Are you sure want to exit?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        finishAndRemoveTask()
                    }
                })
                .show()
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
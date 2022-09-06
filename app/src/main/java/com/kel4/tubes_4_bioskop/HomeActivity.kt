package com.kel4.tubes_4_bioskop

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kel4.tubes_4_bioskop.fragments.LoginFragment

class HomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(LoginFragment())
    }

    fun changeFragment(fragment: Fragment?){
        if(fragment != null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.textView, fragment)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.bottom_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.play){
            //changeFragment(FragmentPlay())
        } else if(item.itemId == R.id.upcoming){
            //changeFragment(FragmentUpcoming())
        } else if(item.itemId == R.id.ticket){
            //changeFragment(FragmentTicket())
        }
        return super.onOptionsItemSelected(item)
    }
}
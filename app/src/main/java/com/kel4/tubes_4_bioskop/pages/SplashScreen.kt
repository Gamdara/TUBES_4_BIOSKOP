package com.kel4.tubes_4_bioskop.pages

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this, AuthActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, 3000)
    }
}
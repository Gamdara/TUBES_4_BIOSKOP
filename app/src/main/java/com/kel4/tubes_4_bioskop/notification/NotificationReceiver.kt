package com.kel4.tubes_4_bioskop.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.kel4.tubes_4_bioskop.pages.AuthActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val message = intent.getStringExtra("toastMessage")
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        context?.startActivity(
            Intent(context, AuthActivity::class.java)
                .putExtra("username", username)
                .putExtra("password", password)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
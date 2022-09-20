package com.kel4.tubes_4_bioskop.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.ActivityMainBinding
import com.kel4.tubes_4_bioskop.databinding.ActivityProfileBinding
import com.kel4.tubes_4_bioskop.entity.User
import com.kel4.tubes_4_bioskop.viewModels.ProfileViewModel
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    var binding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)

        val sp = getSharedPreferences("user", 0)
        val id : Int = sp.getInt("id", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val loggedUser : User = db.noteDao().getUser(id).first()
            Log.d("user",loggedUser.toString())
            binding!!.txtUsername.text = loggedUser.username
            binding!!.txtEmail.text = loggedUser.email
            binding!!.txtTanggal.text = loggedUser.tanggal
            binding!!.txtTelepon.text = loggedUser.telp
        }

        binding!!.btnEdit.setOnClickListener{
            val mainIntent = Intent(this, UpdateProfileActivity::class.java)
            this.startActivity(mainIntent)
            finish()
        }

        setContentView(binding?.root)

    }
}
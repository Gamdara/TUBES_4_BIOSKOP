package com.kel4.tubes_4_bioskop.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.ActivityProfileBinding
import com.kel4.tubes_4_bioskop.databinding.ActivityUpdateProfileBinding
import com.kel4.tubes_4_bioskop.entity.User
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateProfileActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    var binding: ActivityUpdateProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
//        binding.lifecycleOwner = this

        val sp = getSharedPreferences("user", 0)
        val id : Int = sp.getInt("id", 0)

//        CoroutineScope(Dispatchers.IO).launch {
//            val loggedUsers : List<User> = db.noteDao().getUser(id)
//            val loggedUser = loggedUsers[0]
//            Log.d("userini",loggedUser.toString())
//            binding!!.teUser.setText(loggedUser.username)
//            binding!!.teEmail.setText(loggedUser.email)
//            binding!!.teTanggal.setText(loggedUser.tanggal)
//            binding!!.teTelp.setText(loggedUser.telp)
//        }

        binding!!.btnSimpan.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val loggedUsers : List<User> = db.noteDao().getUser(id)
                val logged = loggedUsers[0]
                db.noteDao().updateUser(
                    User(
                        logged.id,
                        binding!!.teUser.text.toString(),
                        binding!!.tePass.text.toString(),
                        binding!!.teEmail.text.toString(),
                        binding!!.teTanggal.text.toString(),
                        binding!!.teTelp.text.toString(),
                        )
                )
                finish()
            }
        }

        setContentView(binding?.root)

    }
}
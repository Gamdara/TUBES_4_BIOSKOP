package com.kel4.tubes_4_bioskop.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.api.UserApi
import com.kel4.tubes_4_bioskop.databinding.ActivityMainBinding
import com.kel4.tubes_4_bioskop.databinding.ActivityProfileBinding
import com.kel4.tubes_4_bioskop.entity.ResponseUser
import com.kel4.tubes_4_bioskop.entity.User
import com.kel4.tubes_4_bioskop.viewModels.ProfileViewModel
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    var binding: ActivityProfileBinding? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        val sp = getSharedPreferences("user", 0)
        val id : Int = sp.getInt("id", 0)
        queue = Volley.newRequestQueue(this)

        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, UserApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val user = gson.fromJson(response, ResponseUser::class.java)

                binding!!.txtUsername.setText(user.data.username)
                binding!!.txtEmail.setText(user.data.email)
                binding!!.txtTanggal.setText(user.data.tanggal)
                binding!!.txtTelepon.setText(user.data.telp)

                Toast.makeText(this@ProfileActivity,"Data berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@ProfileActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)


        binding!!.btnEdit.setOnClickListener{
            val mainIntent = Intent(this, UpdateProfileActivity::class.java)
            this.startActivity(mainIntent)
            finish()
        }

        setContentView(binding?.root)

    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding?.layoutLoading?.root?.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding?.layoutLoading?.root?.visibility = View.INVISIBLE
        }
    }
}
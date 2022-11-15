package com.kel4.tubes_4_bioskop.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.api.UserApi
import com.kel4.tubes_4_bioskop.databinding.ActivityProfileBinding
import com.kel4.tubes_4_bioskop.databinding.ActivityUpdateProfileBinding
import com.kel4.tubes_4_bioskop.entity.ResponseUser
import com.kel4.tubes_4_bioskop.entity.User
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class UpdateProfileActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    var binding: ActivityUpdateProfileBinding? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
//        binding.lifecycleOwner = this

        val sp = getSharedPreferences("user", 0)
        val id : Int = sp.getInt("id", 0)
        queue = Volley.newRequestQueue(this)

        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, UserApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val user = gson.fromJson(response, ResponseUser::class.java)

                binding!!.teUser.setText(user.data.username)
                binding!!.teEmail.setText(user.data.email)
                binding!!.teTanggal.setText(user.data.tanggal)
                binding!!.teTelp.setText(user.data.telp)

                Toast.makeText(this@UpdateProfileActivity,"Data berhasil diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@UpdateProfileActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@UpdateProfileActivity, e.message, Toast.LENGTH_SHORT).show()
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
        binding!!.camera.setOnClickListener{
            val mainIntent = Intent(this, CameraActivity::class.java)
            this.startActivity(mainIntent)
            finish()
        }
        binding!!.btnSimpan.setOnClickListener{
            setLoading(true)
            val user = User(
                id,
                binding!!.teUser.text.toString(),
                binding!!.tePass.text.toString(),
                binding!!.teEmail.text.toString(),
                binding!!.teTanggal.text.toString(),
                binding!!.teTelp.text.toString(),
            )
            val stringRequest: StringRequest = object :
                StringRequest(Method.PUT, UserApi.UPDATE_URL + id, Response.Listener { response ->
                    val gson = Gson()

                    var mahasiswa = gson.fromJson(response, ResponseUser::class.java)

                    if (mahasiswa != null)
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Data berhasil diupdate",
                            Toast.LENGTH_SHORT
                        ).show()

                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()

                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)

                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@UpdateProfileActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(user)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
            queue!!.add(stringRequest)
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
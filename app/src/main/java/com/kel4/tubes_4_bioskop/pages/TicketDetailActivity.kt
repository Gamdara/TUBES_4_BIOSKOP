package com.kel4.tubes_4_bioskop.pages

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
import com.kel4.tubes_4_bioskop.api.TicketApi
import com.kel4.tubes_4_bioskop.databinding.ActivityTicketDetailBinding
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.ResponseData
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class TicketDetailActivity : AppCompatActivity() {

    var binding: ActivityTicketDetailBinding? = null
    private var id: Int = 0
    private var movieId: Int = 0
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(this)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        id = intent.getIntExtra("intent_id", 0)
        Log.d("tiketnya", id.toString())
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]

        setLoading(true)

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TicketApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, ResponseData::class.java)
                setLoading(false)
                if(mahasiswa != null)
                    Toast.makeText(this@TicketDetailActivity, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                val temp = mahasiswa.data[0]
                binding!!.time.setText(mahasiswa.data[0].time)
                binding!!.tvSeat.setText(mahasiswa.data[0].seat.toString())
                binding!!.tvJudul.setText(mahasiswa.data[0].movie?.judul)
                binding!!.sinopsis.setText(mahasiswa.data[0].movie?.sinopsis)
        //        binding!!.poster.setImageResource(temp.movie!!.poster)
                Log.d("MOVIEee",mahasiswa.data[0].movie!!.poster.toString())
                binding!!.ticketId.setText(id)
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@TicketDetailActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception){
                    Toast.makeText(this@TicketDetailActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        queue!!.add(stringRequest)

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
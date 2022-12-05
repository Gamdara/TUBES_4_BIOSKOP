package com.kel4.tubes_4_bioskop.pages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.api.TicketApi
import com.kel4.tubes_4_bioskop.databinding.ActivityEditTicketBinding
import com.kel4.tubes_4_bioskop.databinding.ActivityMainBinding
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.ResponseCreate
import com.kel4.tubes_4_bioskop.entity.ResponseData
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.notification.NotificationReceiver
import com.rama.gdroom_a_10735.room.Constant
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.android.synthetic.main.activity_edit_ticket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class EditTicketActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private var id: Int = 0
    private var movieId: Int = 0
    private var binding: ActivityEditTicketBinding? = null
    private val CHANNEL_BUY ="channel_buy_notificiation"
    private var queue: RequestQueue? = null
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        queue = Volley.newRequestQueue(this)
        binding = ActivityEditTicketBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupView()
        setupListener()
    }
    fun setupView(){

        val intentType = intent.getIntExtra("intent_type", 1)
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]
        edit_movie.setText(movie.judul)

        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }
    private fun setupListener() {

        button_save.setOnClickListener{
            var valid = true
            if(binding!!.editKursi.text.isEmpty()) {
                binding!!.editKursi.setError("Nomor kursi harus diisi")
                valid = false
            }
            if(binding!!.editTime.text.isEmpty()) {
                binding!!.editTime.setError("Jam harus diisi")
                valid = false
            }
            if(!valid) return@setOnClickListener

            var temp :Ticket =Ticket(0,movieId, edit_kursi.text.toString(), edit_time.text.toString(),null)
            createNotificationChannel()
            sendNotification(temp)
            createTicket()
        }
        button_update.setOnClickListener {
            var valid = true
            if(binding!!.editKursi.text.isEmpty()) {
                binding!!.editKursi.setError("Nomor kursi harus diisi")
                valid = false
            }
            if(binding!!.editTime.text.isEmpty()) {
                binding!!.editTime.setError("Jam harus diisi")
                valid = false
            }
            if(!valid) return@setOnClickListener
            else
            updateTicket(intent.getIntExtra("intent_id", 0))
        }
    }
    fun getNote() {
        id = intent.getIntExtra("intent_id", 0)

        setLoading(true)

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TicketApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, ResponseData::class.java)
                setLoading(false)
                if(mahasiswa != null)
                    Toast.makeText(this@EditTicketActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()

                binding?.editTime?.setText(mahasiswa.data[0].time.toString())
                binding?.editKursi?.setText(mahasiswa.data[0].seat.toString())

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@EditTicketActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception){
                    Toast.makeText(this@EditTicketActivity, e.message, Toast.LENGTH_SHORT).show()
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
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val buyNotif = NotificationChannel(CHANNEL_BUY, name,  NotificationManager.IMPORTANCE_DEFAULT).apply{
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(buyNotif)
        }
    }

    private fun  sendNotification(ticket: Ticket){
        val builder = NotificationCompat.Builder(this, CHANNEL_BUY)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Pembelian Berhasil")
            .setContentText("Tiket berhasil dibeli")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(MovieList.listOfNowPlaying[ticket.id_movie].sinopsis)
                .setSummaryText("Rincian Tiket")
                .setBigContentTitle(MovieList.listOfNowPlaying[ticket.id_movie].judul)
            )

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }


    private fun getTicketById(id: Int) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET,
                TicketApi.GET_BY_ID_URL + id, Response.Listener { response ->
                    val gson = Gson()
                    val ticket = gson.fromJson(response, Ticket::class.java)

//                    binding!!.editTime.setText(ticket.editTime)
//                    binding!!.editKursi.setText(ticket.editKursi)

                    Toast.makeText(
                        this@EditTicketActivity,
                        "Data berhasil diambil!",
                        Toast.LENGTH_SHORT
                    ).show()
                    setLoading(false)
                },
                Response.ErrorListener { error ->
                    setLoading(false)

                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@EditTicketActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@EditTicketActivity, e.message, Toast.LENGTH_SHORT).show()
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
    }

    private fun createTicket(){



        setLoading(true)
        val mahasiswa = Ticket(
            0,
            movieId + 3,
            binding!!.editKursi.text.toString(),
            binding!!.editTime.text.toString(),
            null
        )
        Log.d("createti",binding!!.editTime.text.toString())
        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, TicketApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, ResponseCreate::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@EditTicketActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@EditTicketActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception){
                    Toast.makeText(this@EditTicketActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray{
                val gson = Gson()
                val requestBody = gson.toJson(mahasiswa)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }

    private fun updateTicket(id: Int){


        setLoading(true)

        val mahasiswa = Ticket(
            id,
            movieId + 3,
            binding!!.editKursi.text.toString(),
            binding!!.editTime.text.toString(),
            null
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, TicketApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, ResponseCreate::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@EditTicketActivity, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@EditTicketActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception){
                    Toast.makeText(this@EditTicketActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray{
                val gson = Gson()
                val requestBody = gson.toJson(mahasiswa)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }


    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding?.layoutLoading?.root?.visibility = View.VISIBLE
        }
        else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding?.layoutLoading?.root?.visibility = View.INVISIBLE
        }
    }
}
package com.kel4.tubes_4_bioskop.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.api.UserApi
import com.kel4.tubes_4_bioskop.databinding.FragmentSignupBinding
import com.kel4.tubes_4_bioskop.entity.User
import com.kel4.tubes_4_bioskop.notification.NotificationReceiver
import com.kel4.tubes_4_bioskop.pages.AuthActivity
import com.rama.gdroom_a_10735.room.UserDB
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class SignupFragment : Fragment() {

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notificationId1 = 101
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private var queue: RequestQueue? = null
    private var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var phoneMinimum = "^[+]?[0-9]{10,13}$"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        queue = Volley.newRequestQueue(requireContext())
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        var view : View = binding.root
        createNotificationChannel()
        val button : Button = binding.button
        binding.button.setOnClickListener {
            val db by lazy { UserDB(requireContext()) }
            val username : String = binding.tilUsername?.getEditText()?.getText().toString()
            val password : String = binding.tilPassword?.getEditText()?.getText().toString()
            if(username.isEmpty()) {
                binding.tilUsername.setError("Username tidak boleh kosong")
                return@setOnClickListener
            }

            if(binding.tilEmail.editText?.text.toString().isEmpty()) {
                binding.tilEmail.setError("Email tidak boleh kosong")
                return@setOnClickListener
            }

            if(!binding.tilEmail.editText?.text.toString().matches(emailPattern.toRegex())) {
                binding.tilEmail.setError("Email tidak valid")
                return@setOnClickListener
            }

            if(binding.tilTanggal.editText?.text.toString().isEmpty()) {
                binding.tilTanggal.setError("Tanggal tidak boleh kosong")
                return@setOnClickListener
            }

            if(binding.tilTelp.editText?.text.toString().isEmpty()) {
                binding.tilTelp.setError("Nomor telepon tidak boleh kosong")
                return@setOnClickListener
            }
            if(!binding.tilTelp.editText?.text.toString().matches(phoneMinimum.toRegex())) {
                binding.tilTelp.setError("Nomor telepon Minimal 10 dan Maksimal 13")
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                binding.tilPassword.setError("Password tidak boleh kosong")
                return@setOnClickListener
            }

            register()

        }

        val tvLogin : TextView = view.findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener{
            val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
            ft.replace(R.id.fragmentContainerView, LoginFragment())
            ft.commit()
        }
        return view
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)

        }
    }

    fun sendNotification(){
        val intent : Intent = Intent(context, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent : PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val broadcastIntent : Intent = Intent(context, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", " Black Adam sedang tayang")
        broadcastIntent.putExtra("username", binding?.tilUsername?.editText?.text.toString())
        broadcastIntent.putExtra("password", binding?.tilPassword?.editText?.text.toString())

        val actionIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

//        val icon = BitmapFactory.decodeResource(
//            context?.resources,
//            com.kel4.tubes_4_bioskop.R.drawable.poster_blackadam
//        )
        val icon : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.poster_blackadam)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_person_24)
            .setContentTitle("Register Success!")
            .setContentText("Login untuk melihat banyak film menarik!")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.YELLOW)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setLargeIcon(icon)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(icon)
                .bigLargeIcon(null))
            .addAction(R.mipmap.ic_launcher, "Login Sekarang", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId1, builder.build())
        }
    }

    private fun register(){
        binding.button.showLoading()
        val mahasiswa = User(
            0,
            binding.tilUsername?.getEditText()?.getText().toString(),
            binding.tilPassword?.getEditText()?.getText().toString(),
            binding.tilEmail?.getEditText()?.getText().toString(),
            binding.tilTanggal?.getEditText()?.getText().toString(),
            binding.tilTelp?.getEditText()?.getText().toString()
        )
        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, UserApi.REGISTER_URL, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, User::class.java)

                if(mahasiswa != null)
                    Toast.makeText(requireContext(), "Berhasil Register", Toast.LENGTH_SHORT).show()

                sendNotification()

                val fragment : Fragment = LoginFragment()
                val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
                ft.addToBackStack(null);
                ft.replace(R.id.fragmentContainerView, fragment)
                ft.commit()

                binding.button.hideLoading()
            }, Response.ErrorListener { error ->
                binding.button.hideLoading()
                Log.d("volleyerr",error.toString())
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(requireContext(), errors.getString("message"), Toast.LENGTH_SHORT).show()
                    Log.d("volleyerr",errors.getString("message"))
                }
                catch (e:Exception){
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    Log.d("volleyerr",e.message.toString())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
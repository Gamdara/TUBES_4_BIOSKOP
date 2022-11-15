package com.kel4.tubes_4_bioskop.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.api.UserApi
import com.kel4.tubes_4_bioskop.constant.HttpsTrustManager
import com.kel4.tubes_4_bioskop.constant.HttpsTrustManager.Companion.ignoreAllSSLErrors
import com.kel4.tubes_4_bioskop.databinding.FragmentLoginBinding
import com.kel4.tubes_4_bioskop.databinding.FragmentSignupBinding
import com.kel4.tubes_4_bioskop.entity.ResponseCreate
import com.kel4.tubes_4_bioskop.entity.ResponseUser
import com.kel4.tubes_4_bioskop.entity.User
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var bundle : Bundle
    private var queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(requireContext())
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        var view : View = binding.root
        inputUsername = binding.tilUsername
        inputPassword = binding.tilPassword

//        val args = this.arguments
        val extras = activity?.intent?.extras
        Log.d("extras",extras.toString())
//
        val user : String? = extras?.getString("username", "gaada")
        inputUsername.getEditText()?.setText(user)
        val pass : String? = extras?.getString("password", "gaada")
        inputPassword.getEditText()?.setText(pass)

        val button : Button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val db by lazy { UserDB(requireContext()) }

            var username : String = inputUsername?.getEditText()?.getText().toString();
            var password : String = inputPassword?.getEditText()?.getText().toString();

            if(username.isEmpty()) {
                inputUsername.setError("Username tidak boleh kosong")
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                inputPassword.setError("Password tidak boleh kosong")
                return@setOnClickListener
            }


            try{
                login()
            }
            catch(e : Error){
                inputUsername.setError(e.message)
                inputPassword.setError(e.message)
                return@setOnClickListener
            }
        }

        val buttonReset : Button = view.findViewById<Button>(R.id.buttonReset)
        buttonReset.setOnClickListener{
            inputUsername = requireView().findViewById(R.id.tilUsername)
            inputPassword = requireView().findViewById(R.id.tilPassword)
            inputUsername?.getEditText()?.setText("")
            inputPassword?.getEditText()?.setText("")
        }

        val tvRegist : TextView = view.findViewById<TextView>(R.id.tvRegist)
        tvRegist.setOnClickListener{
            val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()

            ft.replace(R.id.fragmentContainerView, SignupFragment())
            ft.commit()
        }
        return view
    }

    private fun login(){
        binding.button.showLoading()
        val user = User(
            0,
            binding.tilUsername?.getEditText()?.getText().toString(),
            binding.tilPassword?.getEditText()?.getText().toString(),
            "","",""
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, UserApi.LOGIN_URL, Response.Listener { response ->
                val gson = Gson()
                Log.d("volleyerr",response.toString())
                val mahasiswa = gson.fromJson(response, ResponseUser::class.java)

                if(mahasiswa != null)
                    Toast.makeText(requireContext(), "Login Sucess", Toast.LENGTH_SHORT).show()

                val sp = requireActivity().getSharedPreferences("user", 0)
                val editor = sp.edit()
                editor.putInt("id", mahasiswa.data.id)
                editor.commit()

                val mainIntent = Intent(getActivity(), MainActivity::class.java)
                getActivity()?.startActivity(mainIntent)
                getActivity()?.finish()

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
                val requestBody = gson.toJson(user)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
    }
}
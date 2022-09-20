package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.FragmentSignupBinding
import com.kel4.tubes_4_bioskop.entity.User
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        var view : View = binding.root
        val button : Button = view.findViewById<Button>(R.id.button)
        binding.button.setOnClickListener {
            val db by lazy { UserDB(requireContext()) }
            val username : String = binding.tilUsername?.getEditText()?.getText().toString()
            val password : String = binding.tilPassword?.getEditText()?.getText().toString()
            if(username.isEmpty()) {
                binding.tilUsername.setError("Username tidak boleh kosong")
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                binding.tilPassword.setError("Password tidak boleh kosong")
                return@setOnClickListener
            }
            if(binding.tilEmail.editText?.text.toString().isEmpty()) {
                binding.tilEmail.setError("Email tidak boleh kosong")
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

            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addUser(
                    User(0,
                        username,
                        password,
                        binding.tilEmail.editText?.text.toString(),
                        binding.tilTanggal.editText?.text.toString(),
                        binding.tilTelp.editText?.text.toString()
                    )
                )
                val fragment : Fragment = LoginFragment()
                val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
                ft.addToBackStack(null);
                ft.replace(R.id.fragmentContainerView, fragment)
                ft.commit()
            }

        }

        val tvLogin : TextView = view.findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener{
            val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
            ft.replace(R.id.fragmentContainerView, LoginFragment())
            ft.commit()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
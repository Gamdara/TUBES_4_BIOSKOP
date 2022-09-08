package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.kel4.tubes_4_bioskop.R

class SignupFragment : Fragment() {
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var inputEmail : TextInputLayout
    private lateinit var inputDate : TextInputLayout
    private lateinit var inputPhone : TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_signup, container, false)
        val button : Button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener{
            inputUsername = requireView().findViewById(R.id.tilUsername)
            inputPassword = requireView().findViewById(R.id.tilPassword)

            val username : String = inputUsername?.getEditText()?.getText().toString()
            val password : String = inputPassword?.getEditText()?.getText().toString()
            if(username.isEmpty()) {
                inputUsername.setError("Username tidak boleh kosong")
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                inputPassword.setError("Password tidak boleh kosong")
                return@setOnClickListener
            }
            val fragment : Fragment = LoginFragment()
            val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
            val bundle = Bundle()
            bundle.putString("username",username); // use as per your need
            bundle.putString("password",password); // use as per your need
            Log.d("bundle",bundle.getString("username").toString())
            fragment.setArguments(bundle);
            ft.addToBackStack(null);
            ft.replace(R.id.fragmentContainerView, fragment)
            ft.commit()
        }

        val tvLogin : TextView = view.findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener{
            val ft: FragmentTransaction = getParentFragmentManager().beginTransaction()
            ft.replace(R.id.fragmentContainerView, LoginFragment())
            ft.commit()
        }
        return view
    }
}
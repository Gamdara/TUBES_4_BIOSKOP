package com.kel4.tubes_4_bioskop.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.entity.User
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var bundle : Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view : View = inflater.inflate(R.layout.fragment_login, container, false)
        inputUsername = view.findViewById(R.id.tilUsername)
        inputPassword = view.findViewById(R.id.tilPassword)

//        val args = this.arguments

///        Log.d("args",args.toString())
//
//        val user : String? = args?.getString("username", "gaada")
//        inputUsername.getEditText()?.setText(user)
//        val pass : String? = args?.getString("password", "gaada")

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

            var isValid : Boolean = false
            CoroutineScope(Dispatchers.IO).launch {
                val users : List<User> = db.noteDao().getUsers()
                for (user in users){
                    Log.d("user",user.toString())
                    if((password == user.password  && username == user.username)){
                        isValid = true
                        val mainIntent = Intent(getActivity(), MainActivity::class.java)
                        getActivity()?.startActivity(mainIntent)
                        getActivity()?.finish()
                    }

                }
                return@launch
            }

            if(!isValid){
                inputUsername.setError("Username atau password salah")
                inputPassword.setError("Username atau password salah")
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

}
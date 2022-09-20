package com.kel4.tubes_4_bioskop.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.ActivityProfileBinding
import com.kel4.tubes_4_bioskop.viewModels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val mainViewModel =ViewModelProvider(this).get(ProfileViewModel::class.java)
        DataBindingUtil.setContentView<ActivityProfileBinding>(
            this, R.layout.activity_profile
        ).apply {
            this.lifecycleOwner = this@ProfileActivity
            this.viewmodel = mainViewModel
        }
    }
}
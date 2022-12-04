package com.kel4.tubes_4_bioskop.pages


import android.os.Bundle
import android.util.Log
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions
import com.adevinta.android.barista.interaction.*
import com.adevinta.android.barista.rule.BaristaRule
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.fragments.LoginFragment
import com.kel4.tubes_4_bioskop.fragments.SignupFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthActivityTest  {
    @get:Rule
    val baristaRule: BaristaRule<AuthActivity> = BaristaRule.create(AuthActivity::class.java)

    @Before
    fun setUp() {
        baristaRule.launchActivity()
    }

//    @Test
    fun testLogin(){
//        val scenario = launchFragmentInContainer<LoginFragment>(Bundle(), R.style.Theme_TUBES_4_BIOSKOP){LoginFragment()}
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.tilUsername,"p")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.etPassword,"p")
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
    }

    @Test
    fun testSignUp(){
        BaristaSleepInteractions.sleep(3000)
        BaristaClickInteractions.clickOn(R.id.tvRegist)
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.etUser,"1")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.tilEmail,"1")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.tilEmail,"1@mail.com")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.tilTanggal,"1")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.etTelp,"1")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.etTelp,"089765436251")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
        BaristaEditTextInteractions.writeTo(R.id.etPass,"1")
        BaristaSleepInteractions.sleep(1000)
        BaristaClickInteractions.clickOn(R.id.button)
        BaristaSleepInteractions.sleep(1000)
    }

}

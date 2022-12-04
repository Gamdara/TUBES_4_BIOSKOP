package com.kel4.tubes_4_bioskop


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions
import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions
import com.adevinta.android.barista.rule.BaristaRule
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.kel4.tubes_4_bioskop.fragments.LoginFragment
import com.kel4.tubes_4_bioskop.fragments.SignupFragment
import com.kel4.tubes_4_bioskop.pages.AuthActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {


    @Before
    fun setUp() {

    }

    @Test
    fun testCreate(){
        val scenario = launchFragmentInContainer<SignupFragment>(Bundle(), R.style.Theme_TUBES_4_BIOSKOP){SignupFragment()}
        onView(withId(R.id.etTelp)).perform(click())
    }
}
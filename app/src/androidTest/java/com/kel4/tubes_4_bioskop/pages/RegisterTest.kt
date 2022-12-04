package com.kel4.tubes_4_bioskop.pages


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions
import com.adevinta.android.barista.interaction.BaristaClickInteractions
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions
import com.adevinta.android.barista.interaction.BaristaSleepInteractions
import com.adevinta.android.barista.rule.BaristaRule
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.fragments.SignupFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterTest {
    @get:Rule
    val baristaRule: BaristaRule<AuthActivity> = BaristaRule.create(AuthActivity::class.java)

    @Before
    fun setUp() {
        baristaRule.launchActivity()
//        baristaRule.activityTestRule.activity.changeFragment(SignupFragment())
    }

    @Test
    fun testCreate(){
//        BaristaVisibilityAssertions.assertDisplayed(R.id.fragmentContainerView);
        BaristaSleepInteractions.sleep(2000)
//        BaristaVisibilityAssertions.assertDisplayed(R.id.button);
//        BaristaClickInteractions.clickOn(R.id.button);
        BaristaEditTextInteractions.typeTo(R.id.tilUsername, "p");
        BaristaClickInteractions.clickOn(R.id.button);
//        BaristaSleepInteractions.sleep(2000);
        BaristaEditTextInteractions.typeTo(R.id.tilPassword, "p");
//        BaristaSleepInteractions.sleep(2000);
        BaristaClickInteractions.clickOn(R.id.button);
    }
}

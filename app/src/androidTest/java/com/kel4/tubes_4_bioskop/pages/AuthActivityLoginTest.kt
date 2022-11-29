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
import com.kel4.tubes_4_bioskop.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthActivityLoginTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AuthActivity::class.java)

    @Test
    fun authActivityLoginTest() {
        val loadingButton = onView(
            allOf(
                withId(R.id.button), withText("Masuk"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        loadingButton.perform(click())
        waitFor(3000)
        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilUsername),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("1"), closeSoftKeyboard())

        val loadingButton2 = onView(
            allOf(
                withId(R.id.button), withText("Masuk"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        loadingButton2.perform(click())
        waitFor(3000)
        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("1"), closeSoftKeyboard())

        val loadingButton3 = onView(
            allOf(
                withId(R.id.button), withText("Masuk"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        loadingButton3.perform(click())
        waitFor(3000)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long) : ViewAction? {
        return object: ViewAction{
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }
            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
            override fun getDescription(): String {
                return "wait for "+delay + " miliseconds"
            }
        }
    }
}

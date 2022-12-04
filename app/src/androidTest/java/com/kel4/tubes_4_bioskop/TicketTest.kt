package com.kel4.tubes_4_bioskop

import com.adevinta.android.barista.interaction.BaristaEditTextInteractions
import com.adevinta.android.barista.rule.BaristaRule
import com.kel4.tubes_4_bioskop.pages.EditTicketActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TicketTest {
    @get:Rule
    val baristaRule: BaristaRule<MainActivity> = BaristaRule.create(MainActivity::class.java)

    @Before
    fun setUp() {
        baristaRule.launchActivity()
    }

    @Test
    fun testCreate(){

    }
}
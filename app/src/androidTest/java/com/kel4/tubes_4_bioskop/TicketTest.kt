package com.kel4.tubes_4_bioskop

import com.adevinta.android.barista.interaction.*
import com.adevinta.android.barista.rule.BaristaRule
import com.kel4.tubes_4_bioskop.pages.EditTicketActivity
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TicketTest {
    @get:Rule
    val baristaRule: BaristaRule<MainActivity> = BaristaRule.create(MainActivity::class.java)
    var delay: Long = 1000

    @Before
    fun setUp() {
        baristaRule.launchActivity()
        BaristaSleepInteractions.sleep(delay + 2000)
    }

    @Test
    fun AtestCreate() {
        BaristaListInteractions.clickListItemChild(R.id.rv_playing, 1, R.id.imageView);
        BaristaSleepInteractions.sleep(delay)
        BaristaClickInteractions.clickOn(R.id.btnBuy)
        BaristaSleepInteractions.sleep(delay)
        BaristaClickInteractions.clickOn(R.id.button_save)
        BaristaSleepInteractions.sleep(delay)
        BaristaEditTextInteractions.typeTo(R.id.edit_time, "1")
        BaristaSleepInteractions.sleep(delay)
        BaristaClickInteractions.clickOn(R.id.button_save)
        BaristaSleepInteractions.sleep(delay)
        BaristaEditTextInteractions.typeTo(R.id.edit_kursi, "1")
        BaristaSleepInteractions.sleep(delay)
        BaristaClickInteractions.clickOn(R.id.button_save)
        BaristaSleepInteractions.sleep(delay + 2000)
    }

    @Test
    fun BtestRead() {
        BaristaMenuClickInteractions.clickMenu(R.id.page_3)
        BaristaSleepInteractions.sleep(delay)
        BaristaSwipeRefreshInteractions.refresh(R.id.sr_mahasiswa)
        BaristaSleepInteractions.sleep(delay)
        BaristaSleepInteractions.sleep(delay + 2000)
    }

    @Test
    fun CtestUpdate() {
        BaristaMenuClickInteractions.clickMenu(R.id.page_3)
        BaristaSleepInteractions.sleep(delay)
        BaristaSwipeRefreshInteractions.refresh(R.id.sr_mahasiswa)
        BaristaSleepInteractions.sleep(delay)
        BaristaListInteractions.clickListItemChild(R.id.rv_ticket, 0, R.id.btnEdit);
        BaristaSleepInteractions.sleep(delay)
        BaristaEditTextInteractions.clearText(R.id.edit_kursi)
        BaristaEditTextInteractions.clearText(R.id.edit_time)
        BaristaClickInteractions.clickOn(R.id.button_update)
        BaristaSleepInteractions.sleep(delay)
        BaristaEditTextInteractions.typeTo(R.id.edit_time, "baru")
        BaristaSleepInteractions.sleep(delay)
        BaristaClickInteractions.clickOn(R.id.button_update)
        BaristaSleepInteractions.sleep(delay)
        BaristaEditTextInteractions.typeTo(R.id.edit_kursi, "baru")
        BaristaSleepInteractions.sleep(delay)
        BaristaClickInteractions.clickOn(R.id.button_update)
        BaristaSleepInteractions.sleep(delay)
        BaristaSleepInteractions.sleep(delay)
        BaristaSwipeRefreshInteractions.refresh(R.id.sr_mahasiswa)
        BaristaSleepInteractions.sleep(delay)
        BaristaSleepInteractions.sleep(delay + 2000)
    }

    @Test
    fun DtestDelete() {
        BaristaMenuClickInteractions.clickMenu(R.id.page_3)
        BaristaSleepInteractions.sleep(delay)
        BaristaSwipeRefreshInteractions.refresh(R.id.sr_mahasiswa)
        BaristaSleepInteractions.sleep(delay)
        BaristaListInteractions.clickListItemChild(R.id.rv_ticket, 0, R.id.btnDelete);
        BaristaSleepInteractions.sleep(delay + 2000)
        BaristaDialogInteractions.clickDialogPositiveButton();
        BaristaSleepInteractions.sleep(delay)
        BaristaSwipeRefreshInteractions.refresh(R.id.sr_mahasiswa)
        BaristaSleepInteractions.sleep(delay + 2000)
    }
}
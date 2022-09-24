package com.kel4.tubes_4_bioskop.pages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.kel4.tubes_4_bioskop.MainActivity
import com.kel4.tubes_4_bioskop.R
import com.kel4.tubes_4_bioskop.databinding.ActivityMainBinding
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.notification.NotificationReceiver
import com.rama.gdroom_a_10735.room.Constant
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.android.synthetic.main.activity_edit_ticket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTicketActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    private var id: Int = 0
    private var movieId: Int = 0
    private var binding: ActivityMainBinding? = null
    private val CHANNEL_BUY ="channel_buy_notificiation"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ticket)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupView()
        setupListener()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]
        edit_movie.setText(movie.judul)

        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }
    private fun setupListener() {

        button_save.setOnClickListener{
        createNotificationChannel()
            CoroutineScope(Dispatchers.IO).launch {
                db.ticketDao().add(
                    Ticket(0,movieId,
                        edit_kursi.text.toString(),
                        edit_time.text.toString(),
                    )
                )

                finish()
            }
            sendNotification()
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ticketDao().update(
                    Ticket(id,movieId,
                        edit_kursi.text.toString(),
                        edit_time.text.toString(),
                    )
                )
                startActivity(Intent(this@EditTicketActivity, MainActivity::class.java))
                finish()
            }
        }
    }
    fun getNote() {
        id = intent.getIntExtra("intent_id", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.ticketDao().getTicket(id)[0]
            edit_kursi.setText(notes.seat)
            edit_time.setText(notes.time)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val buyNotif = NotificationChannel(CHANNEL_BUY, name,  NotificationManager.IMPORTANCE_DEFAULT).apply{
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(buyNotif)
        }
    }

    private fun  sendNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_BUY)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Pembelian Berhasil")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("fkdljfdldkfj;ldaksjfkladj;flja;lkjdfljadslfjaddfdsfafjdfad" +
                        "fdl;akjf;lkdf;lkaj;flkjda;lkfjadljflk;adsjfladjflk;dfjlkdjflakdfjdaffjdlfjdjjj" +
                        "adjflkjadlkfjad;lkfjad;sljf;ladkjajlkfjad;lksfjl;akdjf;lkdsajf;lkdjfkadj;flkad" +
                        "jf;lkadjfkldas;lkfja;dljf;lkdasjf;lkadjs;lfjas;ldkfj;lkadsjfl;kadljfl;kasdjf;l" +
                        "jdlskfjklda;fjadslkfj;sdalkfj;ladjf;lajdl;fkajld;kfjlajfl;adjfl;kajdl;fjadl;kfj;")
                .setSummaryText("Big Summary")
                .setBigContentTitle("BIGSTYLEAAAAAAAAAAAAAAAAAAAAAAAA"))
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Anda telah berhasil membeli")
                .addLine("Anda telah berhasil membeli2")
                .addLine("Anda telah berhasil membeli3")
                .setSummaryText("+3 more"))



        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

}
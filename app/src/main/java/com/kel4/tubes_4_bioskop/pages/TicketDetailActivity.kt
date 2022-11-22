package com.kel4.tubes_4_bioskop.pages

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.kel4.tubes_4_bioskop.api.TicketApi
import com.kel4.tubes_4_bioskop.databinding.ActivityTicketDetailBinding
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.ResponseData
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TicketDetailActivity : AppCompatActivity() {

    var binding: ActivityTicketDetailBinding? = null
    private var id: Int = 0
    private var movieId: Int = 0
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(this)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        id = intent.getIntExtra("intent_id", 0)
        Log.d("tiketnya", id.toString())
        movieId = intent.getIntExtra("movie_id", 0)
        val movie = MovieList.listOfNowPlaying[movieId]

        setLoading(true)

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TicketApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val mahasiswa = gson.fromJson(response, ResponseData::class.java)
                setLoading(false)
                if(mahasiswa != null)
                    Toast.makeText(this@TicketDetailActivity, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                val temp = mahasiswa.data[0]
                binding!!.time.setText(mahasiswa.data[0].time)
                binding!!.tvSeat.setText(mahasiswa.data[0].seat.toString())
                binding!!.tvJudul.setText(mahasiswa.data[0].movie?.judul)

                binding!!.sinopsis.setText(mahasiswa.data[0].movie?.sinopsis)
                binding!!.poster.setImageResource(mahasiswa.data[0].movie!!.poster)
                binding?.button?.setOnClickListener { createPdf(
                    mahasiswa.data[0].movie?.judul.toString(),
                    mahasiswa.data[0].seat.toString(),
                    mahasiswa.data[0].time.toString(),
                    mahasiswa.data[0].movie?.sinopsis.toString(),
                    mahasiswa.data[0].movie!!.poster,
                ) }
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@TicketDetailActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                }
                catch (e:Exception){
                    Toast.makeText(this@TicketDetailActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        queue!!.add(stringRequest)

        setContentView(binding?.root)
    }


    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding?.layoutLoading?.root?.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding?.layoutLoading?.root?.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(FileNotFoundException::class)
    private fun createPdf(judul: String, seat: String, time: String, desc:String, poster:Int){
        //ini berguna untuk akses Writing ke Storage HP kalian dalam mode Download.
        //harus diketik jangan COPAS!!!!
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath, "your ticket.pdf")
        FileOutputStream(file)

        //inisaliasi pembuatan PDF
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(poster)

        //penambahan gambar pada Gambar atas
        val bitmap = (d as BitmapDrawable?)!!.bitmap
        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap, 200, 400, false
        )
        val stream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapData = stream.toByteArray()

        val imageData = ImageDataFactory.create(bitmapData)

        val image = Image(imageData)
        val namapengguna = Paragraph("Atma Cinema").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
                        Berikut adalah
                        Detail Tiket Anda
                        """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        //proses pembuatan table
        val width = floatArrayOf(200f, 200f)
        val table = Table(width)
        //pengisian table dengan data-data
        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Judul")))
        table.addCell(Cell().add(Paragraph(judul)))
        table.addCell(Cell().add(Paragraph("Seat")))
        table.addCell(Cell().add(Paragraph(seat)))
        table.addCell(Cell().add(Paragraph("Time")))
        table.addCell(Cell().add(Paragraph(time)))
        table.addCell(Cell().add(Paragraph("Sinopsis")))
        table.addCell(Cell().add(Paragraph(desc)))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(Cell().add(Paragraph("Tanggal Buat PDF")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a")
        table.addCell(Cell().add(Paragraph("Pukul Pembuatan")))
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        //pembuatan QR CODE secara generate dengan bantuan IText7
        val barcodeQRCode = BarcodeQRCode("$judul".trimIndent())
        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(HorizontalAlignment.CENTER)

        document.add(image)
        document.add(namapengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
        Toast.makeText(this, "Pdf Created", Toast.LENGTH_LONG).show()
    }
}
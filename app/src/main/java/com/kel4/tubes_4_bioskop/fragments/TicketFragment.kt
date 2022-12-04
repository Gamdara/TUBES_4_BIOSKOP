package com.kel4.tubes_4_bioskop.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.kel4.tubes_4_bioskop.RVTicketAdapter
import com.kel4.tubes_4_bioskop.api.TicketApi
import com.kel4.tubes_4_bioskop.databinding.FragmentTicketBinding
import com.kel4.tubes_4_bioskop.entity.ResponseData
import com.kel4.tubes_4_bioskop.entity.Ticket

import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.Arrays


class TicketFragment : Fragment() {
    private var adapter: RVTicketAdapter? = null

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    private var queue: RequestQueue? = null

    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101

        private const val TAG = "MAIN_TAG"
    }

    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermissions: Array<String>

    private var imageUri: Uri? = null

    private var barcodeScannerOption: BarcodeScannerOptions? = null
    private var barcodeScanner: BarcodeScanner? = null

    fun refresh(){
        val ft = parentFragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(requireContext())
        _binding = FragmentTicketBinding.inflate(inflater, container, false)


        var view : View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.srMahasiswa.setOnRefreshListener ({ allTicket() })
        binding.svMahasiswa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

//        Linear formats: Codabar, Code 39, Code 93, Code 128, EAN-8, EAN-13, ITF, UPC-A, UPC-E
//        2D formats: Aztec, Data Matrix, PDF417, QR Code

        barcodeScannerOption = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build()
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOption!!)

        binding.button.setOnClickListener{pickImageGallery()}

        val rvProduk = binding.rvTicket
        adapter = RVTicketAdapter(ArrayList(), requireContext(), this@TicketFragment)
        rvProduk.layoutManager = LinearLayoutManager(requireContext())
        rvProduk.adapter = adapter
        allTicket()
    }

    private fun allTicket(){
        binding.srMahasiswa.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, TicketApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()

                val mahasiswa : Array<Ticket> = gson.fromJson(response, ResponseData::class.java).data.toTypedArray()
                Log.d("allticket", mahasiswa[0].movie?.judul.toString())
                adapter!!.setData(mahasiswa)
                adapter!!.filter.filter(binding.svMahasiswa!!.query)
                binding.srMahasiswa!!.isRefreshing = false

                if(!mahasiswa.isEmpty())
                    Toast.makeText(requireContext(), "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(requireContext(), "Data kosong", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->
                binding.srMahasiswa.isRefreshing = false
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                catch (e: Exception){
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }}
        queue!!.add(stringRequest)
    }

    public fun deleteTicket(id: Int){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TicketApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)
                val gson = Gson()
                val mahasiswa  = gson.fromJson(response, Ticket::class.java)

                if(mahasiswa != null)
                    Toast.makeText(requireContext(), "Data berhasil diambil", Toast.LENGTH_SHORT).show()

                allTicket()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireContext(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                catch (e: Exception){
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }}
        queue!!.add(stringRequest)
    }

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.layoutLoading.root.visibility = View.VISIBLE
        }
        else{
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.layoutLoading.root.visibility = View.INVISIBLE
        }
    }

    private fun detectResultFromImage() {

        try {
            val inputImage = InputImage.fromFilePath(requireContext(), imageUri!!)

            val barcodeResult = barcodeScanner?.process(inputImage)
                ?.addOnSuccessListener { barcodes ->
                    extractbarcodeQrCodeInfo(barcodes)
                }
                ?.addOnFailureListener{ e ->
                    Log.d(TAG,"detectResultFromImage: ", e)
                    showToast("failed Scanning due to ${e.message}")
                }
        }catch (e: Exception){
            Log.d(TAG,"detectResultFromImage: ", e)
            showToast("Failed Due to ${e.message}")
        }
    }

    private fun extractbarcodeQrCodeInfo(barcodes: List<Barcode>) {
        for (barcode in barcodes) {
            val bound = barcode.boundingBox
            val corners = barcode.cornerPoints

            val rawValue = barcode.rawValue
            Log.d(TAG,"extractbarcodeQrCodeInfo: rawValue: $rawValue")

            binding.svMahasiswa!!.setQuery(rawValue!!, true)

            val valueType = barcode.valueType
            when(valueType) {
                Barcode.TYPE_WIFI -> {
                    val typeWifi = barcode.wifi
                    val ssid = "${typeWifi?.ssid}"
                    val password = "${typeWifi?.password}"
                    var encryptionType = "${typeWifi?.encryptionType}"

                    if (encryptionType == "1"){
                        encryptionType = "OPEN"
                    } else if (encryptionType == "2"){
                        encryptionType = "WPA"
                    } else if (encryptionType == "3"){
                        encryptionType = "WEP"
                    }

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_WIFI")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: ssid: $ssid")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: password: $password")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: encryptionType: $encryptionType")

                    showToast(
                        "TYPE_WIFI \n ssid: $ssid \npassword: $password \nencryptionType: $encryptionType" +
                                "\n\nrawValue : $rawValue")

                }
                Barcode.TYPE_URL -> {
                    val typeUrl = barcode.url
                    val title = "${typeUrl?.title}"
                    val url = "${typeUrl?.url}"

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_URL")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: title: $title")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: url: $url")

                    showToast(
                        "TYPE_URL \ntitle: $title \nurl: $url \n\nrawValue : $rawValue")

                }
                Barcode.TYPE_EMAIL -> {
                    val typeEmail = barcode.email
                    val address = "${typeEmail?.address}"
                    val body = "${typeEmail?.body}"
                    val subject = "${typeEmail?.subject}"

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_EMAIL")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: address: $address")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: body: $body")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: subject: $subject")

                    showToast(
                        "TYPE_EMAIL \ntitle: $address \nurl: $body \nsubject: $subject \n\nrawValue : $rawValue")


                }
                Barcode.TYPE_CONTACT_INFO -> {
                    val typeContact = barcode.contactInfo
                    val title = "${typeContact?.title}"
                    val organisasi = "${typeContact?.organization}"
                    val name = "${typeContact?.name}"
                    val phone = "${typeContact?.phones}"

                    Log.d(TAG,"extractbarcodeQrCodeInfo: TYPE_CONTACT_INFO")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Title: $title")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Organization: $organisasi")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: nama: $name")
                    Log.d(TAG,"extractbarcodeQrCodeInfo: Phone: $phone")

                    showToast(
                        "TYPE_CONTACT_INFO " +
                                "\ntitle: $title " +
                                "\nnama: $name " +
                                "\norganization: $organisasi " +
                                "\nPhone : $phone" +
                    "\n\nrawValue : $rawValue")

                }
                else -> {
                    showToast(
                        "rawValue: $rawValue")
                }


            }


        }

    }

    private fun pickImageGallery() {
        if (!checkStoragePermision()) {
            requestStoragePermission()
            return
        }

        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data

            imageUri = data!!.data
            Log.d(TAG,"galleryActivityResultLauncher: imageUri: $imageUri")

//            binding.imageTv.setImageURI(imageUri)
            detectResultFromImage()
        }else {
            showToast("Dibatalkan ..............")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    private fun checkStoragePermision(): Boolean {
        val result = (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
        return result
    }

    private fun requestStoragePermission(){
        ActivityCompat.requestPermissions(requireActivity(),storagePermissions, STORAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted){
                        pickImageGallery()
                    }else
                    {
                        showToast("Storage are required...")
                    }
                }
            }

        }
    }
}
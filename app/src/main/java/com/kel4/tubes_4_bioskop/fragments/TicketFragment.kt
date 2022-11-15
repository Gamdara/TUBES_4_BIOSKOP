package com.kel4.tubes_4_bioskop.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
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
                Log.d("allticket", mahasiswa[0].movie.toString())
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
}
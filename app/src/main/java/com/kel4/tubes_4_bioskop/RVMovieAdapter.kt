package com.kel4.tubes_4_bioskop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.entity.Movie

class RVMovieAdapter(private val data: Array<Movie>) : RecyclerView.Adapter<RVMovieAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_movie, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNama.text = "Judul : "+currentItem.judul
        holder.tvDetails.text = "Rating : "+currentItem.rating.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.tvJudul)
        val tvDetails : TextView = itemView.findViewById(R.id.tvGenre)
    }
}
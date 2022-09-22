package com.kel4.tubes_4_bioskop

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.pages.ProfileActivity
import com.kel4.tubes_4_bioskop.pages.TicketActivity
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.rv_item_movie.view.*

class RVPlayingAdapter(private val data: Array<Movie>
) : RecyclerView.Adapter<RVPlayingAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_movie, parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val currenItem = data[position]
        holder.tvJudul.text = currenItem.judul
        holder.image.setImageResource(currenItem.poster)

    }

    override fun getItemCount() : Int{
        return data.size
    }

    class viewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudul)
        val image : ImageView = itemView.findViewById((R.id.imageView))

    }

}
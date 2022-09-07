package com.kel4.tubes_4_bioskop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.entity.Ticket

class RVTicketAdapter(private val data: Array<Ticket>) : RecyclerView.Adapter<RVTicketAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ticket, parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val currenItem = data[position]
        holder.tvJudul.text = currenItem.movie.judul
        holder.image.setImageResource(currenItem.movie.poster)
        holder.time.text = currenItem.time
        holder.seat.text = currenItem.seat
    }

    override fun getItemCount() : Int{
        return data.size
    }

    class viewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudul)
        val image : ImageView = itemView.findViewById((R.id.imageView))
        val time : TextView = itemView.findViewById(R.id.tvJam)
        val seat : TextView = itemView.findViewById(R.id.tvSeat)


    }
}
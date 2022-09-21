package com.kel4.tubes_4_bioskop

import android.os.Build
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.fragments.TicketFragment
import java.util.Collections.addAll


class RVTicketAdapter(private var data: Array<Ticket>) : RecyclerView.Adapter<RVTicketAdapter.viewHolder>() {

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
        holder.delete.setOnClickListener(){
            Ticket.remove(Ticket.listOfTicket, position)
            notifyItemChanged(position)
            setData(Ticket.listOfTicket)
            notifyDataSetChanged()
        }

    }

    fun setData(data2: Array<Ticket>) {
        val result = data.toMutableList()
        result.clear()
        result.addAll(data2)
        data = result.toTypedArray()
    }
    override fun getItemCount() : Int{
        return data.size
    }

    class viewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudul)
        val image : ImageView = itemView.findViewById((R.id.imageView))
        val time : TextView = itemView.findViewById(R.id.tvJam)
        val seat : TextView = itemView.findViewById(R.id.tvSeat)
        val delete: Button = itemView.findViewById(R.id.btnDelete)
    }



}
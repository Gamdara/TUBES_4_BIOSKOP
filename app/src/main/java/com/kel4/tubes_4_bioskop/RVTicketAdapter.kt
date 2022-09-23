package com.kel4.tubes_4_bioskop

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.constant.Constant
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.pages.EditTicketActivity
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RVTicketAdapter(private var data: Array<Ticket>) : RecyclerView.Adapter<RVTicketAdapter.viewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ticket, parent,false)
        context = itemView.getContext();
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val tickets = MovieList.listOfNowPlaying
        val currenItem = data[position]

        holder.tvJudul.text = tickets[currenItem.movie].judul
        holder.image.setImageResource(tickets[currenItem.movie].poster)
        holder.time.text = currenItem.time
        holder.seat.text = currenItem.seat
        holder.delete.setOnClickListener(){
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.apply {
                setTitle("Confirmation")
                setMessage("Yakin ingin menghapus?")
                setNegativeButton("Cancel", DialogInterface.OnClickListener
                { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                setPositiveButton("Delete", DialogInterface.OnClickListener
                { dialogInterface, i ->
                    val db by lazy { UserDB(context) }
                    dialogInterface.dismiss()
                    CoroutineScope(Dispatchers.IO).launch {
                        db.ticketDao().delete(currenItem)
                        setData(db.ticketDao().getTickets().toTypedArray())
                        notifyItemChanged(position)
                        notifyDataSetChanged()
                    }
                })
            }
            alertDialog.show()
        }
        holder.edit.setOnClickListener(){
            context?.startActivity(
                Intent(context, EditTicketActivity::class.java)
                    .putExtra("intent_id", currenItem.id)
                    .putExtra("intent_type", Constant.TYPE_UPDATE)
                    .putExtra("movie_id", currenItem.movie)
            )
            notifyItemChanged(position)
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
        val edit: Button = itemView.findViewById(R.id.btnEdit)
    }



}
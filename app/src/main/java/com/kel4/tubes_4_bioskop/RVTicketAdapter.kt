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
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.constant.Constant
import com.kel4.tubes_4_bioskop.entity.MovieList
import com.kel4.tubes_4_bioskop.entity.Ticket
import com.kel4.tubes_4_bioskop.fragments.TicketFragment
import com.kel4.tubes_4_bioskop.pages.EditTicketActivity
import com.rama.gdroom_a_10735.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


class RVTicketAdapter(
    private var data: List<Ticket>, context: Context, fragment: Fragment
) : RecyclerView.Adapter<RVTicketAdapter.viewHolder>(),Filterable {
    private val context: Context
    private val fragment: Fragment
    private var filteredList: MutableList<Ticket>

    init {
        filteredList = ArrayList<Ticket>(data)
        this.context = context
        this.fragment = fragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_ticket, parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val currenItem = filteredList[position]

        holder.tvJudul.text = currenItem.movie?.judul
        if(currenItem.movie != null)
            holder.image.setImageResource(currenItem.movie.poster)
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
                    if(fragment is TicketFragment) currenItem.id?.let { it1 ->
                        fragment.deleteTicket(
                            it1
                        )
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
                    .putExtra("movie_id", currenItem.id_movie)
            )
            notifyItemChanged(position)
            notifyDataSetChanged()
        }

    }

    fun setData(data2: Array<Ticket>) {
        this.data = data2.toList()
        filteredList = data2.toMutableList()
    }

    override fun getItemCount() : Int{
        return filteredList.size
    }

    class viewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudul)
        val image : ImageView = itemView.findViewById((R.id.imageView))
        val time : TextView = itemView.findViewById(R.id.tvJam)
        val seat : TextView = itemView.findViewById(R.id.tvSeat)
        val delete: Button = itemView.findViewById(R.id.btnDelete)
        val edit: Button = itemView.findViewById(R.id.btnEdit)
    }


    override fun getFilter(): Filter {
        return object : Filter (){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Ticket> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(data)
                }
                else{
                    for(ticket in data){
                        if(ticket.time.lowercase(Locale.getDefault()).contains(charSequenceString.lowercase(Locale.getDefault()))){
                            filtered.add(ticket)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults : FilterResults){
                filteredList.clear()
                filteredList.addAll(filterResults.values as List<Ticket>)
                notifyDataSetChanged()
                Log.d("bindviewpub", filteredList.size.toString())
            }
        }

    }
}
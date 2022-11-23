package com.kel4.tubes_4_bioskop

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kel4.tubes_4_bioskop.constant.Constant
import com.kel4.tubes_4_bioskop.entity.Movie
import com.kel4.tubes_4_bioskop.pages.EditTicketActivity
import com.kel4.tubes_4_bioskop.pages.MovieDetailActivity
import com.kel4.tubes_4_bioskop.pages.TicketDetailActivity


class RVPlayingAdapter(private val data: Array<Movie>) : RecyclerView.Adapter<RVPlayingAdapter.viewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_nowplaying, parent,false)
        context = itemView.getContext();
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currenItem = data[position]
        Log.d("imageid",position.toString())
        Log.d("imageid",data[position].poster.toString())

        holder.tvJudul.text = currenItem.judul
        holder.image.setImageResource(currenItem.poster)
        holder.image.setOnClickListener(){
            context?.startActivity(
                Intent(context, MovieDetailActivity::class.java)
                    .putExtra("movie_id", position)
            )
        }
    }


    override fun getItemCount() : Int{
        return data.size
    }

    class viewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudul)
        val image : ImageView = itemView.findViewById(R.id.imageView)

    }
}
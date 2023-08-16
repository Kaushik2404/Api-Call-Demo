package com.example.apicalldemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apicalldemo.OnClickIteamMovie
import com.example.apicalldemo.R
import com.example.apicalldemo.modal.Movie

class MovieAdapter(
    private val context: Context,
    private var demoModal: List<Movie?>,
    private val onClickItemMovie: OnClickIteamMovie,
) : RecyclerView.Adapter<MovieViewHolder>() {

    fun filterList(filterList: List<Movie>) {
        demoModal = filterList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_list, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return demoModal.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//w500
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+demoModal[position]?.backdropPath)
            .into(holder.movieImage)
        holder.movieName.text = demoModal[position]?.title

        holder.itemMovie.setOnClickListener { onClickItemMovie.getPost(position) }

//       holder.bindView(postModal[position])

    }
}

class MovieViewHolder(Item: View) : RecyclerView.ViewHolder(Item) {

    val movieName: TextView = Item.findViewById(R.id.MovieName)
    val movieImage: ImageView = Item.findViewById(R.id.MovieImage)
    val itemMovie: LinearLayout = Item.findViewById(R.id.itemMovie)

}
package com.example.trendz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trendz.R
import com.example.trendz.models.Result
import com.example.trendz.utils.Constants.IMG_URL_INIT_PATH


class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private var movieslist = emptyList<Result>()

    class MovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView
        val movieLogo:ImageView
        init {
            movieTitle = itemView.findViewById(R.id.movie_title)
            movieLogo = itemView.findViewById(R.id.movie_logo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_view,parent,false))
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val currentItem = movieslist[position]

        holder.apply {
            movieTitle.text = currentItem.title
            Glide.with(ctx)
                .load(IMG_URL_INIT_PATH+currentItem.backdrop_path)
                .into(movieLogo)
        }

    }

    override fun getItemCount(): Int {
        return movieslist.size

    }

    fun setData(movies:List<Result>){
        this.movieslist = movies
        notifyDataSetChanged()
    }



}

package com.example.trendz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.trendz.R

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MovieGenreAdapter(val genreList:List<com.example.trendz.models.Movie.Genre>): RecyclerView.Adapter<MovieGenreAdapter.MovieGenreListViewHolder>() {

    class MovieGenreListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val genreName: ExtendedFloatingActionButton = itemView.findViewById(R.id.genre_title_id)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreListViewHolder {
        return MovieGenreListViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.genre_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieGenreListViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val currentItem = genreList[position]

        holder.apply {
            genreName.text = currentItem.name
        }

    }

    override fun getItemCount(): Int {
        return genreList.size
    }


}
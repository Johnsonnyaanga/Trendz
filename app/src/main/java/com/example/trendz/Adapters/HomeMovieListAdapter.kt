package com.example.trendz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.trendz.R
import com.example.trendz.fragments.HomeFragment.HomeFragmentDirections
import com.example.trendz.models.Result
import com.example.trendz.utils.Constants.IMG_URL_INIT_PATH


class HomeMovieListAdapter: RecyclerView.Adapter<HomeMovieListAdapter.HMovieListViewHolder>() {
    private var movieslist = emptyList<Result>()

    class HMovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView
        val movieLogo:ImageView
        val movieCard:ConstraintLayout
        init {
            movieTitle = itemView.findViewById(R.id.movie_title)
            movieLogo = itemView.findViewById(R.id.movie_logo)
            movieCard = itemView.findViewById(R.id.movie_view)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HMovieListViewHolder {
        return HMovieListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HMovieListViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val currentItem = movieslist[position]

        holder.apply {
            movieCard.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment2(currentItem)
                itemView.findNavController().navigate(action)
                val circularProgressDrawable = CircularProgressDrawable(ctx)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()


            }
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

package com.example.trendz.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trendz.R
import com.example.trendz.models.Actors.Cast
import com.example.trendz.models.Result
import com.example.trendz.utils.Constants

class ActorsListAdapter(val list:List<Cast>):RecyclerView.Adapter<ActorsListAdapter.ActorsListViewHolder>() {


    class ActorsListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val actorProfileImage: ImageView = itemView.findViewById(R.id.actor_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsListViewHolder {
        return ActorsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.actors_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorsListViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val currentItem = list[position]
       holder.apply {
           Glide.with(ctx)
               .load(Constants.IMG_URL_INIT_PATH + currentItem.profile_path)
               .into(actorProfileImage)
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}
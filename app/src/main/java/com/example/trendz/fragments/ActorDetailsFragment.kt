package com.example.trendz.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.trendz.MainActivity
import com.example.trendz.R
import com.example.trendz.fragments.MovieDetailsFragment.MovieDetailsFragmentArgs
import com.example.trendz.models.Actors.Cast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActorDetailsFragment : Fragment(R.layout.fragment_actor_details) {

    lateinit var args:ActorDetailsFragmentArgs
    @Inject
    lateinit var mainActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = arguments?.let {
           ActorDetailsFragmentArgs.fromBundle(it)
        }!!
        Log.d("castdata",args.Cast.toString())
        //mainActivity.toastMessage(args.Cast.name)

    }



}
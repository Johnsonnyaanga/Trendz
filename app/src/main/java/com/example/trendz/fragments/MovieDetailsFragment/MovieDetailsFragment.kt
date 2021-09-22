package com.example.trendz.fragments.MovieDetailsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.trendz.Adapters.ActorsListAdapter
import com.example.trendz.Adapters.MovieGenreAdapter
import com.example.trendz.R
import com.example.trendz.fragments.MovieDetailsFragment.MovieDetailsFragmentArgs.Companion.fromBundle
import com.example.trendz.models.Actors.Cast
import com.example.trendz.models.Movie.Genre
import com.example.trendz.utils.Constants
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
private  lateinit var args:MovieDetailsFragmentArgs
lateinit var circularProgressDrawable:CircularProgressDrawable
val movieDetailsViewModel: MovieDetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //progress_movieDetails.visibility = VISIBLE
        movieDetailsParentLayout.visibility = GONE
        before_load.visibility = VISIBLE



        //toobar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_movieDetails)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //(requireActivity() as AppCompatActivity).supportActionBar?.title = "Details"



        //setSupportActionBar(toolbar)
        val navHost = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_cont) as NavHostFragment
        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)


        circularProgressDrawable = CircularProgressDrawable(requireActivity())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        args = arguments?.let {
            fromBundle(it)
        }!!
        Log.d("argsdata",args.movieDetails.toString())



        //get All movie Actors
        movieDetailsViewModel.fetchMovieActors(
            args.movieDetails.id,
            API_KEY,
            LANG_US
        )

        movieDetailsViewModel.fetchMovieDetails(
                args.movieDetails.id,
                API_KEY,
                LANG_US
        )



        getMovieActors()
        getMovieGenres()

        movieDetailsViewModel.movieDetailsResponse.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful){
                movieDetailsParentLayout.visibility = VISIBLE
                before_load.visibility = GONE
                //progress_movieDetails.visibility = GONE

                it.body()?.let { movie ->
                    movie_rate.text = movie.vote_average.toString()
                    movieDetails_title.text = movie.title
                    movieDetails_overview_text.text = movie.overview
                    Glide.with(requireContext())
                        .load(Constants.IMG_URL_INIT_PATH +movie.poster_path)
                        .placeholder(circularProgressDrawable)
                        .into(movieDetails_back_drop_image)
                    Glide.with(requireContext())
                        .load(Constants.IMG_URL_INIT_PATH +movie.backdrop_path)
                        .placeholder(circularProgressDrawable )
                        .into(movieDetails_small_image)

                }
            }
        })




    }

    override fun onStart() {
        super.onStart()
    }





    fun initRecyclerviewActors(list:List<Cast>){
        actors_recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity(),
                LinearLayoutManager.HORIZONTAL, false)
            val actorsAdapter = ActorsListAdapter(list)
            adapter = actorsAdapter
        }
    }

    fun initRecyclerviewGenre(genreList:List<Genre>){
        genres_recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity(),
                    LinearLayoutManager.HORIZONTAL, false)
            val genresAdapter = MovieGenreAdapter(genreList)
            adapter = genresAdapter
        }
    }


    private fun getMovieGenres(){
        movieDetailsViewModel.movieDetailsResponse.observe(viewLifecycleOwner, Observer {
            it.body()?.genres.let { listGenres ->
                if (listGenres != null) {
                    initRecyclerviewGenre(listGenres)
                    Log.d("genres",listGenres.toString() )
                }
            }
        })
    }


    private fun getMovieActors(){
        movieDetailsViewModel.movieActorsResponse.observe(viewLifecycleOwner, {
            it.body()?.cast?.let { castList -> initRecyclerviewActors(castList) }
            Log.d("actors", it.body()?.cast.toString())
        })
    }


}
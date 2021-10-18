package com.example.trendz.fragments.MoviesFragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.trendz.Adapters.MovieListAdapter
import com.example.trendz.MainActivity
import com.example.trendz.R
import com.example.trendz.fragments.HomeFragment.HomeFragmentViewModel
import com.example.trendz.fragments.PopularMoviesFragment.PopularViewModel
import com.example.trendz.fragments.UpcomingMoviesFragment.UpcomingMoviesViewModel
import com.example.trendz.models.Result
import com.example.trendz.utils.GridSpacingItemDecoration
import com.example.trendz.utils.InternetCheck
import com.example.trendz.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject


@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val viewmodelTrendingMovies: HomeFragmentViewModel by viewModels()
    private val viewmodelPopularMovies: PopularViewModel by viewModels()
    private val viewmodelUpcomingMovies: UpcomingMoviesViewModel by viewModels()

    @Inject
    lateinit var mainActivity:MainActivity


    private lateinit var args:MoviesFragmentArgs


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = arguments?.let {
            MoviesFragmentArgs.fromBundle(it)
        }!!


        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_movies)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost = requireActivity()
                .supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_cont) as NavHostFragment
        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)



        displayMoviesList()




    }

    private fun initRecyclerviewPopular(list: List<Result>){
        movies_recyclerView.apply {
            val adbpt =MovieListAdapter()
            layoutManager = GridLayoutManager(requireContext(),4)

            adapter = adbpt
            val spacingInPixels = 0
            addItemDecoration(GridSpacingItemDecoration(4,10,false))
            adbpt.setData(list)

        }
    }

    private fun displayMoviesList(){
        when (args.moviesList) {
            "upcomingMovies" -> {
                getUpcomingMovies()
            }
            "popularMovies" -> {
                getPopularMovies()
            }
            else -> {
                getTrendingMovies()
            }
        }

    }

    private fun getPopularMovies() {
        val internetCheck =  InternetCheck(requireActivity().application)


        if (internetCheck.hasInternetConnection()){

            viewmodelPopularMovies.popularMoviesResponse.observe(
                viewLifecycleOwner,
                Observer { res ->

                    when(res){
                        is Resource.Success -> {
                            res.data?.let {
                                initRecyclerviewPopular(it.results)
                            }
                        }

                        is Resource.Error ->{
                            mainActivity.toastMessage("an error occured: ${res.message}")
                        }

                        is Resource.Loading ->{
                            no_internet_layout.visibility = View.VISIBLE
                            main_layout.visibility = View.GONE

                        }


                    }
                })
        }else{
            mainActivity.toastMessage("Network Error")
        }
    }

    private fun getUpcomingMovies() {
        val internetCheck =  InternetCheck(requireActivity().application)


        if (internetCheck.hasInternetConnection()){

            viewmodelUpcomingMovies.upComingMoviesResponse.observe(
                viewLifecycleOwner,
                Observer { res ->
                    when(res){
                        is Resource.Success -> {
                            res.data?.let {
                                initRecyclerviewPopular(it.results)
                            }
                        }

                        is Resource.Error ->{
                            mainActivity.toastMessage("an error occured: ${res.message}")
                        }

                        is Resource.Loading ->{
                            no_internet_layout.visibility = View.VISIBLE
                            main_layout.visibility = View.GONE

                        }


                    }


                })
        }else{
            mainActivity.toastMessage("Network Error")
        }
    }

    private fun getTrendingMovies() {
        val internetCheck =  InternetCheck(requireActivity().application)

        if (internetCheck.hasInternetConnection()){

            viewmodelTrendingMovies._tredingMoviesResponseResource.observe(viewLifecycleOwner, { res ->

                when(res){
                    is Resource.Success -> {
                        res.data?.let {
                            initRecyclerviewPopular(it.results)
                        }
                    }

                    is Resource.Error ->{
                        mainActivity.toastMessage("an error occured: ${res.message}")
                    }

                    is Resource.Loading ->{
                        no_internet_layout.visibility = View.VISIBLE
                        main_layout.visibility = View.GONE

                    }


                }
            })
        }else{
            mainActivity.toastMessage("Network Error")
        }
    }


}


package com.example.trendz.fragments.MoviesFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendz.Adapters.MovieListAdapter
import com.example.trendz.MainActivity
import com.example.trendz.R
import com.example.trendz.fragments.HomeFragment.HomeFragmentViewModel
import com.example.trendz.fragments.PopularMoviesFragment.PopularViewModel
import com.example.trendz.fragments.UpcomingMoviesFragment.UpcomingMoviesViewModel
import com.example.trendz.models.Result
import com.example.trendz.utils.InternetCheck
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

    private fun initRecyclerviewPopular(list:List<Result>){
        movies_recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity().applicationContext,3)
            val adbpt =MovieListAdapter()
            adapter = adbpt
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

            viewmodelPopularMovies.popularMoviesResponse.observe(viewLifecycleOwner, Observer { res->
                try {
                    if (res.isSuccessful){
                        val result =  res.body()
                        if (result != null) {
                            initRecyclerviewPopular(result.results)
                        }
                    }else{
                        mainActivity.toastMessage("Network Error")
                    }
                }catch (e:Exception){
                    Log.d("error_response",e.message.toString())
                    mainActivity.toastMessage("Network Error")
                }


            })
        }else{
            mainActivity.toastMessage("Network Error")
        }
    }

    private fun getUpcomingMovies() {
        val internetCheck =  InternetCheck(requireActivity().application)


        if (internetCheck.hasInternetConnection()){

            viewmodelUpcomingMovies.upComingMoviesResponse.observe(viewLifecycleOwner, Observer { res->
                try {
                    if (res.isSuccessful){
                        val result =  res.body()
                        if (result != null) {
                            initRecyclerviewPopular(result.results)
                        }
                    }else{
                        mainActivity.toastMessage("Network Error")
                    }
                }catch (e:Exception){
                    Log.d("error_response",e.message.toString())
                    mainActivity.toastMessage("Network Error")
                }


            })
        }else{
            mainActivity.toastMessage("Network Error")
        }
    }

    private fun getTrendingMovies() {
        val internetCheck =  InternetCheck(requireActivity().application)

        if (internetCheck.hasInternetConnection()){

            viewmodelTrendingMovies.tredingMoviesResponse.observe(viewLifecycleOwner, { res->
                try {
                    if (res.isSuccessful){
                        val result =  res.body()
                        if (result != null) {

                            initRecyclerviewPopular(result.results)

                        }
                    }else{
                        mainActivity.toastMessage("Network Error")
                    }
                }catch (e:Exception){
                    Log.d("error_response",e.message.toString())
                    mainActivity.toastMessage("Network Error")
                }


            })
        }else{
            mainActivity.toastMessage("Network Error")
        }
    }


}
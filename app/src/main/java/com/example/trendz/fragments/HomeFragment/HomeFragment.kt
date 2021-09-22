package com.example.trendz.fragments.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendz.Adapters.HomeMovieListAdapter
import com.example.trendz.Adapters.MovieListAdapter
import com.example.trendz.R
import com.example.trendz.fragments.PopularMoviesFragment.PopularViewModel
import com.example.trendz.fragments.UpcomingMoviesFragment.UpcomingMoviesViewModel
import com.example.trendz.models.Result
import com.example.trendz.utils.GridSpacingItemDecoration
import com.example.trendz.utils.InternetCheck
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var myResult:List<Result>
    val viewmodelTrendingMovies:HomeFragmentViewModel by viewModels()
    val viewmodelPopularMovies: PopularViewModel by viewModels()
    val viewmodelUpcomingMovies: UpcomingMoviesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayTrendingMovies()
        displayPopularMovies()
        displayUpcomingMovies()



        popular_movies_arrow.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToMoviesFragment("trendingMovies")
            findNavController().navigate(action)

        }
        upcoming_movies_arrow.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToMoviesFragment("trendingMovies")
            findNavController().navigate(action)

        }
        trending_movies_arrow.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToMoviesFragment("trendingMovies")
            findNavController().navigate(action)
        }




    }

    override fun onStart() {
        super.onStart()
    }


    fun initRecyclerview(list:List<Result>){
        recycler_trending.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL, false)
            val adbpt = HomeMovieListAdapter()
            adapter = adbpt
            adbpt.setData(list)
        }
    }

    fun initRecyclerviewUpcoming(list:List<Result>){
        recycler_upcoming_movies.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL, false)
            val adbpt = HomeMovieListAdapter()
            adapter = adbpt
            adbpt.setData(list)
        }
    }

    fun initRecyclerviewPopular(list:List<Result>){
        recycler_popular_movies.apply {
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL, false)
            val adbpt =HomeMovieListAdapter()
            adapter = adbpt
            adbpt.setData(list)
        }
    }

    fun toastMessage(message:String){
        Toast.makeText(requireActivity(),message, Toast.LENGTH_SHORT).show()
    }

    fun displayTrendingMovies() {
        val internetCheck =  InternetCheck(requireActivity().application)

        if (internetCheck.hasInternetConnection()){

        viewmodelTrendingMovies.tredingMoviesResponse.observe(viewLifecycleOwner, { res->
            try {
                if (res.isSuccessful){
                    val result =  res.body()
                    if (result != null) {
                        myResult = result.results



                        initRecyclerview(result.results)
                    }
                }else{
                    toastMessage("Network Error")
                }
            }catch (e:Exception){
                Log.d("error_response",e.message.toString())
                toastMessage("Network Error")
            }


        })
    }else{
           toastMessage("Network Error")
    }


    }

    fun displayPopularMovies(){
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
                        toastMessage("Network Error")
                    }
                }catch (e:Exception){
                    Log.d("error_response",e.message.toString())
                    toastMessage("Network Error")
                }


            })
        }else{
            toastMessage("Network Error")
        }
    }

    fun displayUpcomingMovies(){
        val internetCheck =  InternetCheck(requireActivity().application)


        if (internetCheck.hasInternetConnection()){

            viewmodelUpcomingMovies.upComingMoviesResponse.observe(viewLifecycleOwner, Observer { res->
                try {
                    if (res.isSuccessful){
                        val result =  res.body()
                        if (result != null) {
                            initRecyclerviewUpcoming(result.results)
                        }
                    }else{
                        toastMessage("Network Error")
                    }
                }catch (e:Exception){
                    Log.d("error_response",e.message.toString())
                    toastMessage("Network Error")
                }


            })
        }else{
            toastMessage("Network Error")
        }
    }


}
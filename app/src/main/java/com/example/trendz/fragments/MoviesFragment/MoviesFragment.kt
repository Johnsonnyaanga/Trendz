package com.example.trendz.fragments.MoviesFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        displayMoviesList()




    }

    fun initRecyclerviewPopular(list:List<Result>){
        movies_recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity().applicationContext,3)
            val adbpt =MovieListAdapter()
            adapter = adbpt
            adbpt.setData(list)

        }
    }

    private fun displayMoviesList(){
        val movieTypes = args.moviesList
        if (movieTypes.equals("trendingMovies")){

            getTrendingMovies()

        } else if (movieTypes.equals("popularMovies")){
            getPopularMovies()
        }else{
            getUpcomingMovies()
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
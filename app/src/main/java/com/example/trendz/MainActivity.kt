package com.example.trendz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.trendz.fragments.HomeFragment.HomeFragmentViewModel
import com.example.trendz.fragments.PeopleFragment.PeopleFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNaviagtion()

    }



    fun setUpNaviagtion(){
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_cont) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bttm_nav)
            .setupWithNavController(navController)
    }
    fun toastMessage(message:String){
        Toast.makeText(this@MainActivity,message, Toast.LENGTH_SHORT).show()
    }


}
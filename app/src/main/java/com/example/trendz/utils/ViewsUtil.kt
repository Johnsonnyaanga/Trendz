package com.example.trendz.utils

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ViewsUtil {
    @Inject
    lateinit var ctx:Context

    fun toastMessage(message:String){
        Toast.makeText(ctx,message,Toast.LENGTH_SHORT).show()
    }
}
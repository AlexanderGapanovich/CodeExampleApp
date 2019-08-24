package com.example.codeexampleapp.supportive

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionChecker (val context: Context){

    fun check () : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork:  NetworkInfo?   = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}
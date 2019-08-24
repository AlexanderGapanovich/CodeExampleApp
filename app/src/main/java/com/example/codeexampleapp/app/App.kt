package com.example.codeexampleapp.app

import android.app.Application

import com.example.codeexampleapp.app.di.AppComponent
import com.example.codeexampleapp.app.di.AppVmFactoryModule
import com.example.codeexampleapp.app.di.DaggerAppComponent



class App : Application() {


    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.builder()
            .appVmFactoryModule(AppVmFactoryModule(this))
            .build()
    }

    companion object {
        lateinit var graph: AppComponent
    }
}
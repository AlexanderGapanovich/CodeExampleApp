package com.example.codeexampleapp.app.di

import com.example.codeexampleapp.article.ArticleActivity
import com.example.codeexampleapp.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppVmFactoryModule::class))
interface AppComponent {
    fun injectMain(activity: MainActivity)
    fun injectArt(activity: ArticleActivity)
}
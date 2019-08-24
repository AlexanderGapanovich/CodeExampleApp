package com.example.codeexampleapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.codeexampleapp.article.ArticleViewModel
import com.example.codeexampleapp.main.MainViewModel
import com.example.data.NewsSearchRepository

class AppViewModelFactory(var repository: NewsSearchRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == MainViewModel::class.java) {
            MainViewModel(repository) as T }
        else if (modelClass == ArticleViewModel::class.java) {
            ArticleViewModel(repository) as T
        } else {
            super.create(modelClass)
        }
    }
}
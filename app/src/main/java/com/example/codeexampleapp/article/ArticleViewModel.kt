package com.example.codeexampleapp.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.NewsSearchRepository
import com.example.data.db.ArticleEntity
import com.example.domain.models.Constants

class ArticleViewModel(var repository: NewsSearchRepository) : ViewModel() {

    private var news: LiveData<ArticleEntity>? = null

    fun getArticle(id: Int): LiveData<ArticleEntity>? {
        if (news == null) news = repository.getNewsById(id)
        return news
    }

}

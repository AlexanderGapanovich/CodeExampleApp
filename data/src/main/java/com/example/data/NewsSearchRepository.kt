package com.example.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.db.ArticleEntity
import io.reactivex.Flowable

interface NewsSearchRepository {

    fun getNews(source: String): Flowable<List<ArticleEntity>>
    fun getNewsById(id: Int): LiveData<ArticleEntity>
    fun dataUpdate(source: String)
    fun getVisibilityLoading(): LiveData<Boolean>
}
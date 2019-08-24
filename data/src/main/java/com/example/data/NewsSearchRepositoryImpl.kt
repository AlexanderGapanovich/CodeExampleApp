package com.example.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.db.AppDatabase
import com.example.data.db.ArticleEntity
import com.example.data.network.NewsAPI
import com.example.domain.models.articles.NewsData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class NewsSearchRepositoryImpl(var api: NewsAPI, var db: AppDatabase, var mapper: NewsMapper) : NewsSearchRepository {
    override fun getVisibilityLoading(): LiveData<Boolean> {
        return visibilityLoadig
    }

    private var visibilityLoadig = MutableLiveData<Boolean>()

    override fun getNews(source: String) = db.articleDao().getAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext {
            if (it.isEmpty()) {
                dataUpdate(source)
            }
        }

    override fun getNewsById(id: Int): LiveData<ArticleEntity> {
        return db.articleDao().getById(id)
    }

    override fun dataUpdate(source: String) {
        visibilityLoadig.value = true
        api.searchNews(source).subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (it.articles?.isNotEmpty()!!) db.articleDao().updateAll(mapper.formatNews(it))
                    visibilityLoadig.postValue(false)
                }, {
                    var x = it
                    visibilityLoadig.postValue(false)
                })
    }

}
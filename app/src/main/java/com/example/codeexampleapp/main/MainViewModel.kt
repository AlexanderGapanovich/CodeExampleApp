package com.example.codeexampleapp.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.NewsSearchRepository
import com.example.data.db.ArticleEntity
import com.example.domain.models.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.logging.Handler


class MainViewModel(var repository: NewsSearchRepository) : ViewModel() {

    var visibility = repository.getVisibilityLoading()

    fun getNews(source: String): LiveData<List<ArticleEntity>>? {
        return ArticleLiveData(repository, source)
    }

    fun updateNews(source: String) {
        repository.dataUpdate(source)
    }

    class ArticleLiveData(private val repository: NewsSearchRepository, private val source: String) :
        LiveData<List<ArticleEntity>>() {

        var disposable: Disposable? = null

        override fun onActive() {
            super.onActive()
            disposable = repository.getNews(source)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value = it }, {})
        }

        override fun onInactive() {
            super.onInactive()
            disposable?.dispose()
        }

    }

}


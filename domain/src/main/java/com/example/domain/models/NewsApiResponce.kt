package com.example.domain.models

import com.example.domain.models.articles.Articles

class NewsApiResponce(mdata: List<Articles>? = null, mError: Throwable? = null) {
    var data = mdata
    var error = mError
}

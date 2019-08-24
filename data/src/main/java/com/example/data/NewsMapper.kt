package com.example.data

import com.example.data.db.ArticleEntity
import com.example.domain.models.articles.NewsData

class NewsMapper {
    fun formatNews(data: NewsData): List<ArticleEntity> {
        var news: ArrayList<ArticleEntity> = ArrayList<ArticleEntity>()
        for (item in data.articles!!) {
            item.content?.let {
                ArticleEntity(
                    title = item.title!!
                    , description = item.description!!
                    , urlToImage = item.urlToImage!!
                    , content = it
                )
            }?.let {
                news.add(it)
            }
        }
        return news
    }
}
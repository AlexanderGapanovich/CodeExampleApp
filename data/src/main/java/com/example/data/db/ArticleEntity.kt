package com.example.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
class ArticleEntity (title: String, description: String, urlToImage: String, content: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    val title: String? = title

    @ColumnInfo(name = "description")
    val description: String? = description

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = urlToImage

    @ColumnInfo(name = "content")
    val content: String? = content
}


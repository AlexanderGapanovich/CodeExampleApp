package com.example.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAll(): Flowable<List<ArticleEntity>>

    @Query("DELETE FROM articles")
    fun deleteAll()

    @Query("SELECT * FROM articles WHERE id = :id")
    fun getById(id: Int): LiveData<ArticleEntity>

    @Insert
    fun insertAll(list: List<ArticleEntity>)

    @Transaction
    fun updateAll(list: List<ArticleEntity>) {
        deleteAll()
        insertAll(list)
    }

}
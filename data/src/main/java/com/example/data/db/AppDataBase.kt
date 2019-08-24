package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ArticleEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
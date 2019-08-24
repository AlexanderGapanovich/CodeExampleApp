package com.example.codeexampleapp.app.di

import android.content.Context
import androidx.room.Room
import com.example.codeexampleapp.app.AppViewModelFactory
import com.example.data.NewsMapper
import com.example.data.NewsSearchRepositoryImpl
import com.example.data.db.AppDatabase
import com.example.data.network.NewsAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppVmFactoryModule (val context: Context) {

    @Provides
    internal fun provideFactory(repo: NewsSearchRepositoryImpl): AppViewModelFactory{
        return AppViewModelFactory(repo)
    }

    @Singleton
    @Provides
    internal fun provideRepo(newsAPI: NewsAPI, db: AppDatabase, mapper: NewsMapper): NewsSearchRepositoryImpl{
        return NewsSearchRepositoryImpl(newsAPI, db, mapper)
    }

    @Singleton
    @Provides
    internal fun provideNetwork(): NewsAPI {
        return NewsAPI.create()
    }

    @Provides
    internal fun provideMapper(): NewsMapper {
        return NewsMapper()
    }

    @Singleton
    @Provides
    internal fun provideDb(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "articles"
        ).allowMainThreadQueries().build()
    }
}
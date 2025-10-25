package com.ilesha.newstestapp.di

import android.content.Context
import androidx.room.Room
import com.ilesha.newstestapp.data.local.dao.ArticleDao
import com.ilesha.newstestapp.data.local.dao.RemoteKeysDao
import com.ilesha.newstestapp.data.local.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news.db"
        ).build()
    }

    @Provides
    fun provideArticleDao(newsDatabase: NewsDatabase): ArticleDao {
        return newsDatabase.articleDao()
    }

    @Provides
    fun provideRemoteKeysDao(newsDatabase: NewsDatabase): RemoteKeysDao {
        return newsDatabase.remoteKeysDao()
    }

}
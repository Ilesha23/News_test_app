package com.ilesha.newstestapp.di

import com.ilesha.newstestapp.data.repository.NewsRepositoryImpl
import com.ilesha.newstestapp.domain.repository.NewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class NewsRepositoryModule {

    @Binds
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewRepository

}
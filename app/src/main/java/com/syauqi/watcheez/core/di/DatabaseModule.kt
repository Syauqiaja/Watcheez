package com.syauqi.watcheez.core.di

import android.content.Context
import androidx.room.Room
import com.syauqi.watcheez.core.data.source.local.database.MoviesDatabase
import com.syauqi.watcheez.core.data.source.local.dao.MoviesDao
import com.syauqi.watcheez.core.data.source.local.dao.PeopleDao
import com.syauqi.watcheez.core.data.source.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase = Room.databaseBuilder(
        context,
        MoviesDatabase::class.java, "Movies.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun providePeopleDao(database: MoviesDatabase): PeopleDao = database.getPeopleDao()

    @Provides
    fun provideRemoteKeysDao(database: MoviesDatabase):RemoteKeysDao = database.getRemoteKeysDao()

    @Provides
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao = database.getMovieDao()
}
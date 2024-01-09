package com.syauqi.watcheez.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.syauqi.watcheez.core.data.source.local.dao.MoviesDao
import com.syauqi.watcheez.core.data.source.local.dao.PeopleDao
import com.syauqi.watcheez.core.data.source.local.dao.RemoteKeysDao
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity

@Database(
    entities = [PeopleEntity::class, MovieEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class  MoviesDatabase :RoomDatabase(){
    abstract fun getPeopleDao(): PeopleDao
    abstract fun getMovieDao(): MoviesDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
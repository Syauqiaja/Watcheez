package com.syauqi.watcheez.core.data.source.local

import com.syauqi.watcheez.core.data.source.local.dao.MoviesDao
import com.syauqi.watcheez.core.data.source.local.dao.PeopleDao
import com.syauqi.watcheez.core.data.source.local.dao.RemoteKeysDao
import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity
import com.syauqi.watcheez.core.data.source.local.entity.people_w_movies.PeopleWithMoviesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val peopleDao: PeopleDao
){
    fun getAllPeoples(): Flow<List<PeopleEntity>> = peopleDao.getAllPeoples()
    fun searchPeopleByQuery(query: String) : Flow<List<PeopleEntity>> = peopleDao.searchPeople(query)
    fun getAllPeopleWithMovies(): Flow<List<PeopleWithMoviesEntity>> = peopleDao.getPeopleWithMovies()
    suspend fun insertAlPeople(listPeople: List<PeopleEntity>) = peopleDao.insertAll(listPeople)
}
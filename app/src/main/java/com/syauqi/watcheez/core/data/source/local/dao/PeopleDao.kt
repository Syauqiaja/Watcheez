package com.syauqi.watcheez.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity
import com.syauqi.watcheez.core.data.source.local.entity.people_w_movies.PeopleWithMoviesEntity
import com.syauqi.watcheez.domain.people.model.People
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Query("SELECT * FROM peoples ORDER BY popularity DESC")
    fun getAllPeoples(): Flow<List<PeopleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(peopleData: List<PeopleEntity>)

    @Query("DELETE FROM peoples")
    suspend fun deleteAll()

    @Query("SELECT * FROM peoples WHERE name LIKE :query ORDER BY popularity DESC")
    fun searchPeople(query: String): Flow<List<PeopleEntity>>

    @Transaction
    @Query("SELECT * FROM peoples")
    fun getPeopleWithMovies(): Flow<List<PeopleWithMoviesEntity>>

    @Transaction
    @Query("SELECT * FROM peoples WHERE name LIKE :query ORDER BY popularity")
    fun searchPeopleWithMovies(query: String): Flow<List<PeopleWithMoviesEntity>>

    @Update(PeopleEntity::class)
    suspend fun setPeopleFavorite(people: PeopleEntity)

    @Query("SELECT * FROM peoples WHERE isFavorite = 1")
    fun getFavoritePeople(): Flow<List<PeopleEntity>>

}
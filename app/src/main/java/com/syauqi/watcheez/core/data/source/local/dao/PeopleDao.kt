package com.syauqi.watcheez.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity

@Dao
interface PeopleDao {
    @Query("SELECT * FROM peoples")
    fun getAllPeoples(): List<PeopleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(peopleData: List<PeopleEntity>)

    @Query("DELETE FROM peoples")
    suspend fun deleteAll()

    @Query("SELECT * FROM peoples WHERE name LIKE :query ORDER BY popularity DESC")
    fun searchPeople(query: String): List<PeopleEntity>
}
package com.syauqi.watcheez.core.data.source.local.entity.people_w_movies

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import com.syauqi.watcheez.core.data.source.local.entity.PeopleEntity

data class PeopleWithMoviesEntity(
    @Embedded
    val peopleEntity: PeopleEntity,
    @Relation(
        parentColumn = "peopleId",
        entityColumn = "movieId",
        associateBy = Junction(PeopleMovieCrossRef::class)
    )
    val movies: List<MovieEntity>
)
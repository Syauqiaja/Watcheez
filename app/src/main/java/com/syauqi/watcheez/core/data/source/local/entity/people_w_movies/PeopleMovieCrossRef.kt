package com.syauqi.watcheez.core.data.source.local.entity.people_w_movies

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["peopleId", "movieId"],
    indices = [Index("movieId")])
data class PeopleMovieCrossRef(
    val peopleId: Int,
    val movieId: Int,
)
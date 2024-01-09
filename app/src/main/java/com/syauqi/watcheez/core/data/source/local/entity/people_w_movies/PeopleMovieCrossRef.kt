package com.syauqi.watcheez.core.data.source.local.entity.people_w_movies

import androidx.room.Entity

@Entity(primaryKeys = ["peopleId", "movieId"])
data class PeopleMovieCrossRef(
    val peopleId: Int,
    val movieId: Int,
)
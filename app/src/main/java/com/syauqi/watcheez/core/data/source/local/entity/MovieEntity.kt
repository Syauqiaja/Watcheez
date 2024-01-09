package com.syauqi.watcheez.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movieId")
    val id: Int,
    val title: String,
    val popularity: Double,
    val backdropPath: String,
    val genreIds: List<Int>,
    val posterPath: String,
    val overview: String,
    val video: Boolean
)
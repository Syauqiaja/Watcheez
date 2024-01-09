package com.syauqi.watcheez.domain.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val popularity: Double,
    val backdropPath: String,
    val genreIds: List<Int>,
    val posterPath: String,
    val overview: String,
    val video: Boolean
):Parcelable
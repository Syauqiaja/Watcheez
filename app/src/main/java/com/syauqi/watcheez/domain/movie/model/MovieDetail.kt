package com.syauqi.watcheez.domain.movie.model

import java.util.Date

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val overview: String,
    val popularity: Float,
    val tagline: String,
    val releaseDate: Date,
    val genres: List<String>,
    val duration: Int,
    val productionCompanies: List<String>,
    val revenue: Long
)
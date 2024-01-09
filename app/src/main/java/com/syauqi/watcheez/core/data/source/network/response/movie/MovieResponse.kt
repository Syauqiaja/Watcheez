package com.syauqi.watcheez.core.data.source.network.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("video")
    val video: Boolean,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("popularity")
    val popularity: Double?,
    
    @field:SerializedName("id")
    val id: Int

)
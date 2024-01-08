package com.syauqi.watcheez.core.data.source.network.response.people_detail

import com.google.gson.annotations.SerializedName

data class MovieCredits(

    @field:SerializedName("cast")
    val cast: List<CastItem>?,
)

data class CastItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("character")
    val character: String? = null,

    @field:SerializedName("credit_id")
    val creditId: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("id")
    val id: Int? = null,
)

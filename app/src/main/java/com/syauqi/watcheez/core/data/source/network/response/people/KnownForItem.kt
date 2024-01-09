package com.syauqi.watcheez.core.data.source.network.response.people

import com.google.gson.annotations.SerializedName

data class KnownForItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int>?,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double?,
    
    @field:SerializedName("id")
    val id: Int

)
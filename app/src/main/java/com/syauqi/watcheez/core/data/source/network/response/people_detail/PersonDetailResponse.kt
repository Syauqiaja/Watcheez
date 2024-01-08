package com.syauqi.watcheez.core.data.source.network.response.people_detail

import com.google.gson.annotations.SerializedName

data class PersonDetailResponse(

    @field:SerializedName("birthday")
    val birthday: String? = null,

    @field:SerializedName("images")
    val images: PersonImages,

    @field:SerializedName("gender")
    val gender: Int,

    @field:SerializedName("profile_path")
    val profilePath: String,

    @field:SerializedName("biography")
    val biography: String,

    @field:SerializedName("external_ids")
    val externalIds: ExternalIds,

    @field:SerializedName("place_of_birth")
    val placeOfBirth: String? = null,

    @field:SerializedName("movie_credits")
    val movieCredits: MovieCredits?,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,
)
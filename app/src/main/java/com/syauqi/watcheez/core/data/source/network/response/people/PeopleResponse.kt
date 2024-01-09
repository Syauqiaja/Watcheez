package com.syauqi.watcheez.core.data.source.network.response.people

import com.google.gson.annotations.SerializedName

data class PeopleResponse(

    @field:SerializedName("original_name")
	val originalName: String? = null,

    @field:SerializedName("popularity")
	val popularity: Double? = null,

    @field:SerializedName("known_for")
	val knownFor: List<KnownForItem>? = null,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("profile_path")
	val profilePath: String? = null,

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("gender")
	val gender: Int,
)

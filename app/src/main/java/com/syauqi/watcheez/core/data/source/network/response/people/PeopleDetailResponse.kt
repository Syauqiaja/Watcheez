package com.syauqi.watcheez.core.data.source.network.response.people

import com.google.gson.annotations.SerializedName

data class PeopleDetailResponse(

	@field:SerializedName("birthday")
	val birthday: String? = null,

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@field:SerializedName("known_for_department")
	val knownForDepartment: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null,

	@field:SerializedName("biography")
	val biography: String? = null,

	@field:SerializedName("place_of_birth")
	val placeOfBirth: String? = null,

	@field:SerializedName("popularity")
	val popularity: Any? = null,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,
)

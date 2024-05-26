package com.syauqi.watcheez.core.data.source.network.response.movie

import com.google.gson.annotations.SerializedName
import com.syauqi.watcheez.core.data.source.network.response.people.PeopleResponse

data class CreditResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("cast")
    val casts: List<PeopleResponse>
)
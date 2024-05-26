package com.syauqi.watcheez.domain.people.model

import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.CastItem
import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.ExternalIds
import com.syauqi.watcheez.core.data.source.network.response.people.people_detail.ImageItem

data class PersonDetail (
    val birthday: String? = null,
    val gender: Int,
    val profilePath: String,
    val biography: String,
    val popularity: Double,
    val name: String,
    val id: Int,
    val externalIds: ExternalIds,
    val placeOfBirth: String? = null,
    val images: List<ImageItem>,
    val movieCredits: List<CastItem>?,
)
package com.syauqi.watcheez.core.data.source.local.entity

import com.syauqi.watcheez.utils.enums.Gender


data class PeopleEntity(
    val id: Int,
    val name: String,
    val popularity: Double,
    val photoUrl: String,
    val gender: Int
)
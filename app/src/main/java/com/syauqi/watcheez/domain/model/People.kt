package com.syauqi.watcheez.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class People(
    val id: Int,
    val name: String,
    val popularity: Double,
    val photoUrl: String,
    val gender: Int,
):Parcelable

package com.syauqi.watcheez.domain.model

import android.os.Parcelable
import com.syauqi.watcheez.core.data.source.network.response.people.KnownForItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class People(
    val id: Int,
    val name: String,
    val popularity: Double,
    val photoUrl: String,
    val gender: Int,
    val knownForItem: List<Movie>? = null
):Parcelable

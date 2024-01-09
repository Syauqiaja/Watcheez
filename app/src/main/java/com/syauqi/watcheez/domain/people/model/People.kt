package com.syauqi.watcheez.domain.people.model

import android.os.Parcelable
import com.syauqi.watcheez.domain.movie.model.Movie
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

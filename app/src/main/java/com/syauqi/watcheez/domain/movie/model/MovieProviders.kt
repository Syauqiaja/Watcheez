package com.syauqi.watcheez.domain.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieProviders (
    val link: String,
    val providers: List<WatchProvider>,
):Parcelable{
    @Parcelize
    data class WatchProvider(
        val logoPath: String,
        val providerId: Int,
        val displayPriority: Int,
        val providerName: String
    ):Parcelable
}
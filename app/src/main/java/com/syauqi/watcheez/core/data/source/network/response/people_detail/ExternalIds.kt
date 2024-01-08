package com.syauqi.watcheez.core.data.source.network.response.people_detail

import com.google.gson.annotations.SerializedName

data class ExternalIds(

    @field:SerializedName("imdb_id")
    val imdbId: String? = null,

    @field:SerializedName("tiktok_id")
    val tiktokId: Any? = null,

    @field:SerializedName("twitter_id")
    val twitterId: String? = null,

    @field:SerializedName("youtube_id")
    val youtubeId: Any? = null,

    @field:SerializedName("facebook_id")
    val facebookId: Any? = null,

    @field:SerializedName("instagram_id")
    val instagramId: String? = null
)
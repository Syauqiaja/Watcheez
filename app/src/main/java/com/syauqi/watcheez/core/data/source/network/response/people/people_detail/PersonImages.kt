package com.syauqi.watcheez.core.data.source.network.response.people.people_detail

import com.google.gson.annotations.SerializedName

data class PersonImages(
    @field:SerializedName("profiles")
    val profiles: List<ImageItem>
)

data class ImageItem(

    @field:SerializedName("aspect_ratio")
    val aspectRatio: Double,

    @field:SerializedName("file_path")
    val filePath: String,
)
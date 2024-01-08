package com.syauqi.watcheez.utils

import com.syauqi.watcheez.utils.enums.ImageSize

fun String.asRemoteImagePath(imageSize: ImageSize): String{
    return "https://image.tmdb.org/t/p/${imageSize.value}/$this"
}


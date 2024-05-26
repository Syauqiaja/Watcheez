package com.syauqi.watcheez.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.hoko.blur.HokoBlur
import com.syauqi.watcheez.utils.enums.ImageSize

fun String.asRemoteImagePath(imageSize: ImageSize): String{
    return "https://image.tmdb.org/t/p/${imageSize.value}/$this"
}

fun Bitmap.blurAtRuntime(context: Context, radius: Int = 5): Bitmap{
    return HokoBlur.with(context)
        .mode(HokoBlur.MODE_GAUSSIAN)
        .radius(radius)
        .blur(this)
}


package com.vibes.rv.util

import android.content.Context
import android.net.Uri
import android.util.Size
import coil3.request.ImageRequest
import coil3.request.crossfade

fun requestImage(
    uri: Uri,
    context: Context,
    size: Size
): ImageRequest {
    val pictureSize = coil3.size.Size(size.width, size.height)
    return ImageRequest.Builder(context)
        .data(uri)
        .crossfade(true)
        .size(pictureSize)
        .diskCacheKey(uri.toString() + pictureSize.toString())
        .memoryCacheKey(uri.toString() + pictureSize.toString())
        .build()
}
package com.vibes.rv.ui.component

import android.net.Uri
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AsyncThumbnail(
    uri: Uri?,
    width: Int = 100,
    height: Int = 100,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillBounds,
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null,
    fallback: @Composable () -> Unit
) {
    val context = LocalContext.current
    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(uri) {
        if(uri != null) {
            image = withContext(Dispatchers.IO) {
                try {
                    context.contentResolver.loadThumbnail(
                        uri,
                        Size(width, height),
                        null
                    ).asImageBitmap()
                }catch (_: Exception) {
                    null
                }
            }
        }
    }

    if(image != null) {
        Image(
            image!!,
            uri.toString(),
            modifier,
            alignment,
            contentScale,
            alpha,
            colorFilter,
        )
    } else {
        fallback()
    }
}

package com.vibes.rv.ui.component

import android.net.Uri
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.vibes.rv.util.requestImage

@Composable
fun AsyncThumbnail(
    uri: Uri?,
    modifier: Modifier = Modifier,
    width: Int = 100,
    height: Int = 100,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillBounds,
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null,
    loading: @Composable (() -> Unit)? = null,
    fallback: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = requestImage(
            uri!!,
            context,
            Size(width, height)
        )
    )
    val state by painter.state.collectAsState()

    when (state) {
        is AsyncImagePainter.State.Empty -> {
            fallback()
        }

        is AsyncImagePainter.State.Error -> {
            fallback()
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }

        is AsyncImagePainter.State.Loading -> {
            if (loading != null) {
                loading()
            } else {
                fallback()
            }
        }
    }
}

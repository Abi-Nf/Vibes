package com.vibes.rv.ui.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun AsyncImage(
    source: Uri?,
    imageModifier: Modifier = Modifier,
    parentModifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.FillBounds,
    alpha: Float = 0f,
    colorFilter: ColorFilter? = null,
    fallback: @Composable () -> Unit
) {
    GlideSubcomposition(source, parentModifier) {
        when(state) {
            is RequestState.Success -> Image(
                painter,
                "Async image",
                imageModifier,
                alignment,
                contentScale,
                alpha,
                colorFilter
            )
            is RequestState.Loading -> fallback()
            is RequestState.Failure -> fallback()
        }
    }
}
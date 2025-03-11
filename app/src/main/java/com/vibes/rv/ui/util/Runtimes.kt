package com.vibes.rv.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun <T, R> rememberAndMap(value: T, mapper: (T) -> R): R {
    return remember(value) { mapper(value) }
}

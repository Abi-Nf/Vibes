package com.vibes.rv.ui.util.color

import androidx.compose.ui.graphics.Color

fun Color.invert() = Color(1f - red, 1f - green, 1f - blue, alpha)

fun Color.interpolate(end: Color, factor: Float) = interpolate(this, end, factor)

package com.vibes.rv.ui.util.color

import androidx.compose.ui.graphics.Color

fun interpolate(start: Color, end: Color, factor: Float): Color {
    if(factor <= 0) {
        return start
    }else if(factor >= 1) {
        return end
    }

    return Color(
        start.red + (end.red - start.red) * factor,
        start.green + (end.green - start.green) * factor,
        start.blue + (end.blue - start.blue) * factor,
        start.alpha + (end.alpha - start.alpha) * factor,
    )
}
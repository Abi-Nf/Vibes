package com.vibes.rv.ui.screen.player

internal fun accelerationRevert(value: Float): Float {
    return (1 - (value * 2)).coerceIn(0f, 1f)
}
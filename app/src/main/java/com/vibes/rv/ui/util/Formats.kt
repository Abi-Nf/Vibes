package com.vibes.rv.ui.util

import java.util.Locale

fun Long.toReadableTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(
        Locale.US,
        "%02d:%02d",
        minutes,
        seconds
    )
}
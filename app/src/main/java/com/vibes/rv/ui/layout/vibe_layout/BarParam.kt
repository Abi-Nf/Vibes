package com.vibes.rv.ui.layout.vibe_layout;

data class BarParam<T>(
    val normal: T,
    val minified: T,
) {
    internal fun get(status: VibeBarState): T {
        return when(status) {
            VibeBarState.MINIFIED -> minified
            else -> normal
        }
    }
}
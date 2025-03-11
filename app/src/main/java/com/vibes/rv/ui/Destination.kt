package com.vibes.rv.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination() {
    @Serializable
    data object Home : Destination()
}
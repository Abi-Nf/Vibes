package com.vibes.rv.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination() {
    @Serializable
    data object Home : Destination()

    @Serializable
    data object Albums : Destination()

    @Serializable
    data object Artists : Destination()

    @Serializable
    data object Playlists : Destination()
}
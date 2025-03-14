package com.vibes.rv.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Destination() {
    @Serializable
    data object Tabbed : Destination() {
        @Serializable
        sealed class TabbedDestination : Destination()

        @Serializable
        data object Home : TabbedDestination()

        @Serializable
        data object Albums : TabbedDestination()

        @Serializable
        data object Artists : TabbedDestination()

        @Serializable
        data object Playlists : TabbedDestination()

        @Serializable
        data object Me : TabbedDestination()
    }

    @Serializable
    data object PlayingQueue : Destination()

    @Serializable
    data object Browse : Destination()

    @Serializable
    data class Search(val query: String) : Destination()

    @Serializable
    data object Downloads : Destination()

    @Serializable
    data object Notifications : Destination()

    @Serializable
    data object History : Destination()

    @Serializable
    data object Favorites : Destination()

    @Serializable
    data object Wrapped : Destination()

    @Serializable
    data object PlaylistSurprise : Destination()

    @Serializable
    data class AlbumDetails(val id: Long) : Destination()

    @Serializable
    data class ArtistDetails(val id: Long) : Destination()

    @Serializable
    data object Settings : Destination()

    @Serializable
    data object Equalizer : Destination()
}
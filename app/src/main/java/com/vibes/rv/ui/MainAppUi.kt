package com.vibes.rv.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vibes.rv.VibesViewModel
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.provider.ProvideAppContext
import com.vibes.rv.ui.screen.detail.album.AlbumsDetails
import com.vibes.rv.ui.screen.detail.artist.ArtistDetails
import com.vibes.rv.ui.screen.detail.playlist.PlaylistDetails
import com.vibes.rv.ui.screen.download.Downloads
import com.vibes.rv.ui.screen.equalizer.Equalizer
import com.vibes.rv.ui.screen.favorite.Favorites
import com.vibes.rv.ui.screen.history.History
import com.vibes.rv.ui.screen.home.HomeScreen
import com.vibes.rv.ui.screen.library.AlbumLibrary
import com.vibes.rv.ui.screen.library.ArtistLibrary
import com.vibes.rv.ui.screen.library.PlaylistLibrary
import com.vibes.rv.ui.screen.me.MySettings
import com.vibes.rv.ui.screen.notification.Notifications
import com.vibes.rv.ui.screen.play_queue.PlayingQueue
import com.vibes.rv.ui.screen.search.BrowseSearch
import com.vibes.rv.ui.screen.search.SearchResult
import com.vibes.rv.ui.screen.setting.AppSettings
import com.vibes.rv.ui.screen.wrapped.Wrapped

@Composable
fun MainAppUi(viewModel: VibesViewModel) {
    ProvideAppContext {
        Box(
            Modifier
                .fillMaxSize()
                .background(AppContext.palette.crust)
        ) {
            val controller = rememberNavController()

            NavHost(
                controller,
                startDestination = Destination.Tabbed,
                enterTransition = { scaleIn(initialScale = 0.6f) + fadeIn() },
                exitTransition = { scaleOut() + fadeOut() }
            ) {
                composable<Destination.Tabbed> {
                    val subController = rememberNavController()

                    NavHost(
                        subController,
                        Destination.Tabbed.Home,
                        enterTransition = { slideInHorizontally() + fadeIn() },
                        exitTransition = { slideOutHorizontally() + fadeOut() }
                    ) {
                        composable<Destination.Tabbed.Home> { HomeScreen() }
                        composable<Destination.Tabbed.Artists> { ArtistLibrary() }
                        composable<Destination.Tabbed.Albums> { AlbumLibrary() }
                        composable<Destination.Tabbed.Playlists> { PlaylistLibrary() }
                        composable<Destination.Tabbed.Me> { MySettings() }
                    }
                }

                composable<Destination.PlayingQueue>(
                    enterTransition = { slideInVertically() + fadeIn() },
                    exitTransition = { slideOutHorizontally() + fadeOut() }
                ) { PlayingQueue() }

                composable<Destination.PlaylistSurprise> { PlaylistDetails() }
                composable<Destination.AlbumDetails> { AlbumsDetails() }
                composable<Destination.ArtistDetails> { ArtistDetails() }

                composable<Destination.Browse> { BrowseSearch() }
                composable<Destination.Search> { SearchResult() }

                composable<Destination.Downloads> { Downloads() }
                composable<Destination.Notifications> { Notifications() }

                composable<Destination.History> { History() }
                composable<Destination.Favorites> { Favorites() }
                composable<Destination.Wrapped> { Wrapped() }

                composable<Destination.Settings> { AppSettings() }
                composable<Destination.Equalizer> { Equalizer() }
            }
        }
    }
}

package com.vibes.rv.ui

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
import com.vibes.rv.ui.screen.home.HomeScreen

@Composable
fun MainAppUi(viewModel: VibesViewModel) {
    ProvideAppContext {
        Box(
            Modifier
                .fillMaxSize()
                .background(AppContext.palette.crust)
        ) {
            val controller = rememberNavController()

            NavHost(controller, startDestination = Destination.Tabbed) {

                composable<Destination.Tabbed> {
                    val subController = rememberNavController()

                    NavHost(subController, Destination.Tabbed.Home) {
                        composable<Destination.Tabbed.Home> { HomeScreen() }
                        composable<Destination.Tabbed.Artists> {}
                        composable<Destination.Tabbed.Albums> {}
                        composable<Destination.Tabbed.Playlists> {}
                        composable<Destination.Tabbed.Me> {}
                    }
                }

                composable<Destination.PlayingQueue> {}

                composable<Destination.Browse> {}
                composable<Destination.Search> {}

                composable<Destination.Downloads> {}
                composable<Destination.Notifications> {}

                composable<Destination.History> {}
                composable<Destination.Favorites> {}
                composable<Destination.Wrapped> {}
                composable<Destination.PlaylistSurprise> {}

                composable<Destination.Settings> {}
                composable<Destination.Equalizer> {}
            }
        }
    }
}

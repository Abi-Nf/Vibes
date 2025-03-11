package com.vibes.rv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.provider.ProvideAppContext
import com.vibes.rv.ui.screen.home.HomeScreen

@Composable
fun MainAppUi() {
    ProvideAppContext {
        Box(
            Modifier
                .fillMaxSize()
                .background(AppContext.palette.crust)
        ) {
            val controller = rememberNavController()

            NavHost(controller, Destination.Home) {
                composable<Destination.Home> { HomeScreen() }
            }
        }
    }
}

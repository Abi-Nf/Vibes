package com.vibes.rv.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Header()
        Shortcut()
    }
}

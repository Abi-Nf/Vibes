package com.vibes.rv.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vibes.rv.data.dto.Track
import com.vibes.rv.ui.provider.AppContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val trackFlow = AppContext.tracks
        var tracks by remember { mutableStateOf<List<Track>>(emptyList()) }
        var isLoading by remember { mutableStateOf<Boolean>(true) }
        val coroutineContext = rememberCoroutineScope()

        // ૮(˶ㅠ︿ㅠ)ა This seems to be not working !!!
        LaunchedEffect(trackFlow.collectAsStateWithLifecycle()) {
            coroutineContext.launch(Dispatchers.IO) {
                trackFlow.collect {
                    tracks = it
                    isLoading = false
                }
            }
        }

        Header()
        Shortcut()
        if (isLoading) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            MusicList(tracks)
        }
    }
}
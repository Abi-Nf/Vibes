package com.vibes.rv.ui.screen.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.UserRound
import com.vibes.rv.data.dto.Artist
import com.vibes.rv.data.repository.ArtistRepository
import com.vibes.rv.data.repository.TrackRepository
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.util.player.addTracks
import com.vibes.rv.util.player.playAt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ArtistLibrary() {
    val player = AppContext.player
    val context = LocalContext.current
    var artists by remember { mutableStateOf(emptyList<Artist>()) }
    var isLoading by remember { mutableStateOf<Boolean>(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            ArtistRepository(context)
                .getArtists()?.let {
                    artists = it
                    isLoading = false
                }
        }
    }

    val handlePlayArtistSongs = { id: Long ->
        player?.apply {
            clearMediaItems()
            addTracks(TrackRepository(context).getAllByArtistId(id))
            playAt(0)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            Modifier.padding(12.dp)
        ) {
            Text(
                "Artist",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        if(artists.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if(isLoading) {
                        CircularProgressIndicator()
                        Text(
                            "Loading artists...",
                            fontWeight = FontWeight.Bold,
                        )
                    } else {
                        Text(
                            "˙◠˙",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.em
                        )
                        Text(
                            "No artist was found on your device"
                        )
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                GridCells.Fixed(2),
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(artists) { artist -> CardArtist(artist) { handlePlayArtistSongs(artist.id) } }
            }
        }
    }
}

@Composable
private fun CardArtist(artist: Artist, onClick: () -> Unit) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .aspectRatio(1f)
                .background(colorScheme.surface, CircleShape),
            Alignment.Center
        ) {
            Icon(
                Lucide.UserRound,
                colorScheme.onSurface,
                Modifier.fillMaxSize(0.7f)
            )
        }

        Text(
            artist.name.replace("/", " and "),
            Modifier.padding(horizontal = 6.dp),
            colorScheme.onSurfaceVariant,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


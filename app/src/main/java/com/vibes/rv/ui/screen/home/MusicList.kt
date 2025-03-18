package com.vibes.rv.ui.screen.home

import android.content.Context
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Music3
import com.composables.icons.lucide.Plus
import com.vibes.rv.data.dto.Track
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.color.interpolate
import com.vibes.rv.util.player.playAt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MusicList(tracks: List<Track>) {
    val context = LocalContext.current
    val player = AppContext.player
    var lazyColumnState = rememberLazyListState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Text(
            "All Tracks",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        if (tracks.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "˙◠˙",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.em
                    )
                    Text(
                        "No tracks was found on your device"
                    )
                }
            }
        } else {
            LazyColumn(
                state = lazyColumnState,
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                itemsIndexed(tracks) { index, item ->
                    val playerState by AppContext.playerState.collectAsStateWithLifecycle()
                    val isPlaying = playerState.currentMedia?.mediaId == item.toMediaItem().mediaId
                    MusicListItem(context, item, isPlaying) {
                        player?.apply {
                            clearMediaItems()
                            setMediaItems(tracks.map {
                                it.toMediaItem()
                            })
                            playAt(index)
                        }
                    }
                }
                item {
                    Spacer(Modifier.padding(bottom = 100.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicListItem(context: Context, track: Track, isPlaying: Boolean, onClick: () -> Unit) {
    var imageThumbnail by remember { mutableStateOf<ImageBitmap?>(null) }
    var menuIsOpen by remember { mutableStateOf(false) }
    var playlistModalEnabled by remember { mutableStateOf(false) }
    val modalState = rememberModalBottomSheetState()

    LaunchedEffect(track.thumbnailUri) {
        imageThumbnail = withContext(Dispatchers.IO) {
            try {
                return@withContext context.contentResolver.loadThumbnail(
                    track.source,
                    Size(80, 80),
                    null
                ).asImageBitmap()
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }

    Card(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = {
                onClick()
            }),
    ) {
        ListItem(
            colors = ListItemDefaults.colors().let {
                ListItemColors(
                    if (isPlaying)
                        MaterialTheme.colorScheme.primary.interpolate(
                            MaterialTheme.colorScheme.surface,
                            0.9f
                        ) else it.containerColor,
                    it.headlineColor,
                    it.leadingIconColor,
                    it.overlineColor,
                    it.supportingTextColor,
                    it.trailingIconColor,
                    it.disabledHeadlineColor,
                    it.disabledLeadingIconColor,
                    it.disabledTrailingIconColor
                )
            },
            leadingContent = {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceTint)
                        .size(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageThumbnail != null) {
                        Image(
                            imageThumbnail!!,
                            null,
                            Modifier.fillMaxWidth()
                        )
                    } else {
                        Icon(
                            Lucide.Music3,
                            MaterialTheme.colorScheme.tertiaryContainer,
                        )
                    }
                }
            },
            headlineContent = {
                Text(
                    track.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            supportingContent = {
                Text(
                    track.artist.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            trailingContent = {
                IconButton(onClick = {
                    menuIsOpen = true
                }) {
                    Icon(
                        Lucide.EllipsisVertical,
                        MaterialTheme.colorScheme.inverseSurface
                    )
                }
                DropdownMenu(
                    expanded = menuIsOpen,
                    onDismissRequest = {
                        menuIsOpen = false
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text("Add to playlist") },
                        onClick = {
                            menuIsOpen = false
                            playlistModalEnabled = true
                        },
                        leadingIcon = {
                            Icon(
                                Lucide.Plus,
                                color = MaterialTheme.colorScheme.inverseSurface,
                            )
                        }
                    )
                }
            }
        )
    }

    if (playlistModalEnabled) {
        AddToPlaylistModal(
            state = modalState,
            track = track,
            onClose = {
                playlistModalEnabled = false
            },
        )
    }
}
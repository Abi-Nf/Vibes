package com.vibes.rv.ui.screen.home

import android.content.Context
import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import coil3.compose.AsyncImage
import com.composables.icons.lucide.Disc3
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.shifthackz.catppuccin.palette.Catppuccin
import com.vibes.rv.data.repository.TrackRepository
import com.vibes.rv.ui.component.AsyncThumbnail
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.color.interpolate
import com.vibes.rv.util.player.playAt
import com.vibes.rv.util.requestImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.map
import kotlin.collections.orEmpty

@Composable
fun MusicList() {
    val context = LocalContext.current
    val player = AppContext.player
    var lazyColumnState = rememberLazyListState()
    var tracks by remember { mutableStateOf<List<MediaItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf<Boolean>(true) }

    // Definitely a working code  ദ്ദി(˵ •̀ ᴗ - ˵ ) ✧
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            tracks = TrackRepository(context)
                .getTracks().orEmpty()
                .map { it.toMediaItem() }
            isLoading = false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "All Tracks",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(tracks.size.toString(), Modifier.padding(bottom = 3.dp))
        }
        if (tracks.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if(isLoading) {
                        CircularProgressIndicator()
                        Text(
                            "Loading tracks...",
                            fontWeight = FontWeight.Bold,
                        )
                    } else {
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
            }
        } else {
            LazyColumn(
                state = lazyColumnState,
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                itemsIndexed(tracks) { index, item ->
                    val playerState by AppContext.playerState.collectAsStateWithLifecycle()
                    val isPlaying = playerState.currentMedia?.mediaId == item.mediaId
                    MusicListItem(context, item, isPlaying) {
                        player?.apply {
                            clearMediaItems()
                            setMediaItems(tracks)
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
fun MusicListItem(context: Context, track: MediaItem, isPlaying: Boolean, onClick: () -> Unit) {
    var menuIsOpen by remember { mutableStateOf(false) }
    var playlistModalEnabled by remember { mutableStateOf(false) }
    val modalState = rememberModalBottomSheetState()

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
                    if (track.mediaMetadata.artworkUri != null) {
                        AsyncImage(
                            model = requestImage(
                                track.mediaMetadata.artworkUri!!,
                                context,
                                Size(80,80),
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(Lucide.Disc3, Catppuccin.Latte.Overlay2)
                    }
                }
            },
            headlineContent = {
                Text(
                    track.mediaMetadata.title.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            supportingContent = {
                Text(
                    track.mediaMetadata.artist.toString(),
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
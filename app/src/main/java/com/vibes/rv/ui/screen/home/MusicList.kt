package com.vibes.rv.ui.screen.home

import android.content.Context
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.media3.common.Player
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Music3
import com.vibes.rv.data.dto.Track
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
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
            LazyColumn(state = lazyColumnState) {
                items(tracks) {
                    MusicListItem(player, context, it)
                }
                item {
                    Spacer(Modifier.padding(bottom = 100.dp))
                }
            }
        }
    }
}

@Composable
fun MusicListItem(player: Player?, context: Context, track: Track) {
    var imageThumbnail by remember { mutableStateOf<ImageBitmap?>(null) }

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
        Modifier.clickable(onClick = {
            player?.setMediaItem(track.toMediaItem())
            player?.prepare()
            player?.play()
        })
    ) {
        ListItem(
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
                IconButton(onClick = {}) {
                    Icon(
                        Lucide.EllipsisVertical,
                        MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
        )
    }
}
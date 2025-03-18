package com.vibes.rv.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import com.vibes.rv.data.dto.Track
import com.vibes.rv.data.model.PlaylistItem
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.isUserAppDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToPlaylistModal(state: SheetState, track: MediaItem, onClose: () -> Unit) {
    val database = AppContext.database
    val playlists by database.playlistDao.getPlaylists().collectAsStateWithLifecycle(emptyList())
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = {
            onClose()
        },
        scrimColor = if (!isUserAppDark()) MaterialTheme
            .colorScheme
            .inverseSurface
            .copy(alpha = 0.4f)
        else BottomSheetDefaults.ScrimColor
    ) {
        Column(
            Modifier.padding(25.dp)
        ) {
            Text(
                "Choose a playlist",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            Text("Choose a playlist to add the following song")
            ListItem(
                headlineContent = {
                    Text(
                        track.mediaMetadata.title.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                supportingContent = {
                    Text(
                        track.mediaMetadata.artist.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint,
                ),
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
            if (playlists.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("(ㆁωㆁ)")
                        Text("You have no playlists yet")
                    }
                }
            } else {
                LazyColumn {
                    items(playlists) {
                        ListItem(
                            leadingContent = {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            MaterialTheme.colorScheme.surfaceTint
                                        )
                                        .height(50.dp)
                                        .width(50.dp)
                                ) {
                                    Text(
                                        it.name.first().uppercase(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.em,
                                        modifier = Modifier
                                            .padding(start = 26.dp)
                                    )
                                }
                            },
                            headlineContent = {
                                Text(
                                    it.name,
                                    maxLines = 1,
                                )
                            },
                            modifier = Modifier.clickable(
                                onClick = {
                                    scope.launch {
                                        database.playlistDao.savePlaylistItem(
                                            PlaylistItem(
                                                playlistId = it.id!!,
                                                trackId = track.mediaId.toLong()
                                            )
                                        )
                                        onClose()
                                    }
                                    Toast.makeText(context, "Track saved to ${it.name}", Toast.LENGTH_SHORT).show()
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}
package com.vibes.rv.ui.screen.library

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Delete
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Trash
import com.composables.icons.lucide.X
import com.shifthackz.catppuccin.palette.Catppuccin
import com.vibes.rv.data.VibesDatabase
import com.vibes.rv.data.model.Playlist
import com.vibes.rv.data.repository.TrackRepository
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.isUserAppDark
import kotlinx.coroutines.launch

@Composable
fun PlaylistLibrary() {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        PlaylistLibraryTopBar()
        PlaylistList()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistList() {
    val playlists by AppContext.playlists.collectAsStateWithLifecycle(emptyList())
    var lazyGridState = rememberLazyGridState()
    val player = AppContext.player
    val context = LocalContext.current

    if (playlists.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    "╮(￣▽￣\"\")╭ ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.em
                )
                Text("No playlists yet")
            }
        }
    } else {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            state = lazyGridState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(playlists) {
                val playlistDto by AppContext.database.playlistDao.getById(it.id!!)
                    .collectAsStateWithLifecycle(null)
                Card(
                    Modifier
                        .height(250.dp)
                        .clickable(
                            onClick = {
                                if (playlistDto != null && playlistDto!!.list.isNotEmpty()) {
                                    player?.apply {
                                        clearMediaItems()
                                        var playlistItems = playlistDto!!.list.mapNotNull { dto ->
                                            TrackRepository(context)
                                                .getById(dto.trackId)?.toMediaItem()
                                        }.toList()
                                        setMediaItems(playlistItems)
                                        prepare()
                                        play()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "This playlist is empty ( º＿º )",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ),
                    border = BorderStroke(1.dp, Catppuccin.Latte.Surface2)
                ) {
                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        val firstChar = it.name.first().uppercase()
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceTint)
                                .weight(1f)
                        ) {
                            Text(
                                firstChar,
                                fontWeight = FontWeight.Bold,
                                fontSize = 50.em,
                                modifier = Modifier.padding(start = 80.dp)
                            )
                        }
                        BottomPlaylistInfo(it)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistLibraryTopBar() {
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf<Boolean>(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        Arrangement.SpaceBetween
    ) {
        Text(
            "Playlists",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
        FilledIconButton(
            modifier = Modifier.padding(end = 15.dp),
            onClick = {
                showBottomSheet = true
            },
        ) {
            Icon(
                imageVector = Lucide.Plus,
                tint = MaterialTheme.colorScheme.surface,
                contentDescription = "Add playlist",
            )
        }
    }

    if (showBottomSheet) {
        NewPlaylistModal(bottomSheetState, onClose = {
            coroutineScope.launch {
                showBottomSheet = false
                bottomSheetState.hide()
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaylistModal(state: SheetState, onClose: () -> Unit) {
    val context = LocalContext.current
    val database = AppContext.database
    val scope = rememberCoroutineScope()

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
        var value by remember { mutableStateOf<String>("") }
        Column(
            Modifier.padding(25.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                "Create a playlist",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            OutlinedTextField(
                value = value,
                onValueChange = { it -> value = it },
                label = { Text("Name") },
                placeholder = { Text("My awesome playlist") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        IconButton(onClick = { value = "" }) {
                            Icon(Lucide.Delete, contentDescription = "Clear")
                        }
                    }
                },
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = { onClose() }) {
                    Icon(
                        Lucide.X, "Cancel", Modifier
                            .padding(end = 8.dp)
                            .size(18.dp)
                    )
                    Text("Cancel", color = MaterialTheme.colorScheme.inverseSurface)
                }

                Button(onClick = {
                    Log.d("PlaylistCreationHandler", "Playlist name: $value")
                    if (value.isEmpty()) {
                        Toast.makeText(context, "Please enter a playlist name", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        scope.launch {
                            savePlaylist(database, value)
                            onClose()
                        }
                        Toast.makeText(context, "Playlist saved !", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        Lucide.Check, "Done", Modifier
                            .padding(end = 8.dp)
                            .size(18.dp)
                    )
                    Text("Done", color = MaterialTheme.colorScheme.surface)
                }
            }
        }
    }
}

@Composable
fun BottomPlaylistInfo(playlist: Playlist) {
    val playlistDto by AppContext.database.playlistDao.getById(playlist.id!!)
        .collectAsStateWithLifecycle(null)
    val database = AppContext.database
    val scope = rememberCoroutineScope()
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(
                playlist.name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
            )
            Text(
                playlistDto?.let {
                    "${it.list.size} tracks"
                } ?: "0 tracks",
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
            )
        }
        IconButton(onClick = { menuExpanded = true }) {
            Icon(
                Lucide.EllipsisVertical,
                tint = Catppuccin.Latte.Surface2,
                contentDescription = "more-options"
            )
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Edit Playlist") },
                    onClick = {
                        Toast.makeText(context, "This is not yet implemented", Toast.LENGTH_SHORT)
                            .show()
                        menuExpanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Lucide.Pen,
                            contentDescription = "Edit Playlist"
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete playlist") },
                    onClick = {
                        scope.launch {
                            database.playlistDao.delete(playlist)
                        }
                        Toast
                            .makeText(context, "Playlist deleted !", Toast.LENGTH_SHORT).show()
                        menuExpanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Lucide.Trash,
                            contentDescription = "Delete Playlist"
                        )
                    }
                )
            }
        }
    }
}

private suspend fun savePlaylist(database: VibesDatabase, playlistName: String) {
    database.playlistDao.savePlaylist(Playlist(name = playlistName))
}

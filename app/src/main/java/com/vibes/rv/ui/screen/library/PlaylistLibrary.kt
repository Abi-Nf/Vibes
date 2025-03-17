package com.vibes.rv.ui.screen.library

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Check
import com.composables.icons.lucide.Delete
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.X
import com.vibes.rv.data.model.Playlist
import com.vibes.rv.ui.provider.AppContext
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
    val playlists = remember { mutableListOf<Playlist>() }
    val coroutineScope = rememberCoroutineScope()
    val database = AppContext.database

    LaunchedEffect(playlists) {
        coroutineScope.launch {
            playlists.addAll(database.playlistDao.getPlaylists())
        }
    }
    LazyVerticalGrid(
        GridCells.Fixed(2),
        Modifier
            .fillMaxSize()
    ) {
        items(playlists) {
            Card(
                Modifier
                    .padding(15.dp)
                    .height(150.dp)
            ) {
                Text(it.name)
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
        IconButton(
            modifier = Modifier.padding(end = 15.dp),
            onClick = {
                showBottomSheet = true
            },
        ) {
            Icon(
                imageVector = Lucide.Plus,
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
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = {
            onClose()
        }
    ) {
        val textFieldState = rememberTextFieldState()
        var value by remember { mutableStateOf<String>("") }
        Column(
            Modifier.padding(25.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                "Create a playlist",
                style = MaterialTheme.typography.titleLarge,
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
                    Text("Cancel")
                }

                Button(onClick = {}) {
                    Icon(
                        Lucide.Check, "Done", Modifier
                            .padding(end = 8.dp)
                            .size(18.dp)
                    )
                    Text("Done")
                }
            }
        }
    }
}
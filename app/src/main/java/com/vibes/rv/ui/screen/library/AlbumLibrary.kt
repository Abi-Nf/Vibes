package com.vibes.rv.ui.screen.library

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Play
import com.shifthackz.catppuccin.palette.Catppuccin
import com.vibes.rv.data.dto.Album
import com.vibes.rv.data.repository.TrackRepository
import com.vibes.rv.ui.component.AsyncThumbnail
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.util.player.addTracks
import com.vibes.rv.util.player.playAt

@Composable
fun AlbumLibrary() {
    val player = AppContext.player
    val context = LocalContext.current
    var gridState = rememberLazyGridState()
    val albums by AppContext.albums.collectAsStateWithLifecycle()

    val handlePlayerAlbumSongs = { id: Long ->
        player?.apply {
            clearMediaItems()
            addTracks(TrackRepository(context).getAllByAlbumId(id))
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
                "Albums",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        LazyVerticalGrid(
            GridCells.Fixed(2),
            Modifier
                .weight(1f)
                .padding(horizontal = 15.dp),
            state = gridState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(albums) { album -> CardAlbum(album) { handlePlayerAlbumSongs(album.id) } }
        }
    }
}


@Composable
private fun CardAlbum(album: Album, onClick: () -> Unit) {
    Card(
        Modifier
            .height(250.dp),
        border = BorderStroke(1.dp, Catppuccin.Latte.Surface2)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            val firstChar = album.name.first().uppercase()
            Box(
                Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                AsyncThumbnail(
                    album.image,
                    500,
                    500,
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                ) {
                    Text(
                        firstChar,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.em,
                        modifier = Modifier.padding(start = 80.dp)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        album.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    )
                    Text(
                        album.artist.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                    )
                }
                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        Lucide.Play,
                        color = Catppuccin.Latte.Subtext0
                    )
                }
            }
        }
    }

}

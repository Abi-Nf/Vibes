package com.vibes.rv.ui.screen.player

import android.content.ContentUris
import android.provider.MediaStore
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Disc3
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Music3
import com.vibes.rv.ui.component.AsyncThumbnail
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.layout.vibe_layout.VibeBarState
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.state.MusicState
import com.vibes.rv.ui.util.padding.times
import com.vibes.rv.util.player.playAt
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ColumnScope.VibesPlayerView(
    musicState: MusicState,
    status: VibeBarState,
    heightPercent: Float
) {
    val contentResolver = LocalContext.current.contentResolver
    val player = AppContext.player
    val coroutineScope = rememberCoroutineScope()
    val transition = updateTransition(status, "player-status-content")
    val pagerState = rememberPagerState() { musicState.playingQueueLength }

    LaunchedEffect(musicState.currentIndex) {
        coroutineScope.launch {
            player?.let { player ->
                if(player.currentMediaItemIndex != pagerState.currentPage){
                    pagerState.scrollToPage(musicState.currentIndex)
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        coroutineScope.launch {
            player?.let { player ->
                if(player.currentMediaItemIndex != pagerState.currentPage){
                    player.playAt(pagerState.currentPage)
                }
            }
        }
    }

    AnimatedSafeArea(heightPercent) {
        Box (Modifier.padding(10.dp * accelerationRevert(heightPercent))) {
            CompactPlayer(musicState, transition, heightPercent)

            HorizontalPager(
                pagerState,
                Modifier
                    .defaultMinSize(45.dp)
                    .fillMaxWidth(heightPercent),
                contentPadding = PaddingValues(24.dp) * heightPercent,
            ) {
                val itemRoundness by transition.animateDp(label = "image-roundness") {
                    if(it == VibeBarState.MINIFIED) 24.dp else 16.dp
                }

                val scale by animateFloatAsState(
                    if(it == pagerState.currentPage) 1f else 0.9f,
                    label = "player-item-scale"
                )

                Box(
                    Modifier
                        .scale(scale)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(itemRoundness))
                        .background(MaterialTheme.colorScheme.surfaceTint),
                    Alignment.Center
                ) {
                    val albumId = AppContext.player?.getMediaItemAt(it)?.mediaMetadata?.extras?.getLong("album_id")
                    if(albumId != null) {
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            albumId
                        )
                        AsyncThumbnail(
                            uri,
                            800,
                            800,
                            Modifier.fillMaxSize()
                        ) {
                            Icon(
                                Lucide.Disc3,
                                MaterialTheme.colorScheme.onSurface,
                                Modifier.fillMaxSize(0.7f)
                            )
                        }
                    }else {
                        Icon(
                            Lucide.Music3,
                            MaterialTheme.colorScheme.secondary,
                            Modifier.fillMaxSize(0.6f)
                        )
                    }
                }
            }
        }

        Controls(musicState)
    }
}

package com.vibes.rv

import android.app.Application
import android.content.ComponentName
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.vibes.rv.data.VibesDatabase
import com.vibes.rv.data.model.PlaylistItem
import com.vibes.rv.data.repository.AlbumRepository
import com.vibes.rv.data.repository.ArtistRepository
import com.vibes.rv.data.repository.TrackRepository
import com.vibes.rv.service.PlaybackService
import com.vibes.rv.ui.state.MusicState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
class VibesViewModel(
    private val application: Application,
    val database: VibesDatabase,
): AndroidViewModel(application) {
    var mediaController: MediaController? by mutableStateOf(null)
        private set

    private val _musicState = MutableStateFlow<MusicState>(MusicState(
        currentIndex = 0,
        currentTime = 0L,
        isPlaying = false,
        hasPrev = false,
        hasNext = false,
        repeatMode = Player.REPEAT_MODE_OFF,
        isShuffle = false,
        playingQueueLength = 0,
        currentMedia = null
    ))

    val musicState = _musicState.asStateFlow()

    val tracks get() = TrackRepository(application.applicationContext).fetchTracks().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val albums get() = AlbumRepository(application.applicationContext).fetchAlbums().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val artists get() = ArtistRepository(application.applicationContext).fetchArtists().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val playlists = database.playlistDao.getPlaylists().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        val sessionToken = SessionToken(application, ComponentName(application, PlaybackService::class.java))
        MediaController.Builder(application, sessionToken)
            .buildAsync()
            .apply {
                addListener(
                    {
                        mediaController = get().apply {
                            addListener(handler)
                            _musicState.update {
                                it.copy(
                                    currentIndex = currentMediaItemIndex,
                                    currentMedia = currentMediaItem
                                )
                            }
                        }
                    },
                    MoreExecutors.directExecutor()
                )
            }
    }

    override fun onCleared() {
        super.onCleared()
        mediaController?.removeListener(handler)
        mediaController?.release()
    }

    private fun updateState(player: Player) {
        _musicState.update { currentState ->
            currentState.copy(
                currentIndex = player.currentMediaItemIndex,
                currentTime = player.currentPosition,
                isPlaying = player.isPlaying,
                hasPrev = player.hasPreviousMediaItem(),
                hasNext = player.hasNextMediaItem(),
                repeatMode = player.repeatMode,
                isShuffle = player.shuffleModeEnabled,
                playingQueueLength = player.mediaItemCount
            )
        }
    }


    private val handler = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            updateState(player)

            if(player.isPlaying) {
                viewModelScope.launch {
                    while (player.isPlaying) {
                        _musicState.update {
                            it.copy(
                                currentTime = player.currentPosition
                            )
                        }
                        delay(500)
                    }
                }
            }
        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            super.onTimelineChanged(timeline, reason)
            _musicState.update {
                it.copy(
                    currentMedia = mediaController?.currentMediaItem
                )
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            _musicState.update {
                it.copy(
                    currentMedia = mediaController?.currentMediaItem
                )
            }
        }
    }
}
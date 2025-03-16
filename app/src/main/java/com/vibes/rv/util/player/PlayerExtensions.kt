package com.vibes.rv.util.player

import androidx.media3.common.Player
import com.vibes.rv.data.dto.Track

fun Player.playAt(index: Int) {
    seekTo(index, 0)
    play()
}

fun Player.togglePlayPause() {
    if(isPlaying) {
        pause()
    }else {
        play()
    }
}

fun Player.playPrev() {
    seekToPrevious()
    play()
}

fun Player.playNext() {
    seekToNext()
    play()
}

fun Player.addTrack(track: Track) {
    addMediaItem(track.toMediaItem())
}

fun Player.addTracks(tracks: List<Track>) {
    addMediaItems(tracks.map { it.toMediaItem() })
}

fun Player.addPlayNext(track: Track) {
    addMediaItem(currentMediaItemIndex + 1, track.toMediaItem())
}

fun Player.addAllNext(tracks: List<Track>) {
    addMediaItems(currentMediaItemIndex + 1, tracks.map { it.toMediaItem() })
}

fun Player.triggerLoop(): Int {
    val value = when(repeatMode) {
        Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ONE
        Player.REPEAT_MODE_ONE -> Player.REPEAT_MODE_ALL
        Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_OFF
        else -> Player.REPEAT_MODE_OFF
    }
    repeatMode = value
    return value
}

fun Player.toggleShuffle(): Boolean {
    val value = !shuffleModeEnabled
    shuffleModeEnabled = value
    return value
}
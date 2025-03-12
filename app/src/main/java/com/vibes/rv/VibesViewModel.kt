package com.vibes.rv

import android.app.Application
import android.content.ComponentName
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.vibes.rv.data.VibesDatabase
import com.vibes.rv.service.PlaybackService

@Stable
class VibesViewModel(
    application: Application,
): AndroidViewModel(application) {
    var mediaController: MediaController? by mutableStateOf(null)
        private set

    init {
        val sessionToken = SessionToken(application, ComponentName(application, PlaybackService::class.java))
        MediaController.Builder(application, sessionToken)
            .buildAsync()
            .apply {
                addListener(
                    { mediaController = get() },
                    MoreExecutors.directExecutor()
                )
            }
    }

    override fun onCleared() {
        super.onCleared()
        mediaController?.release()
    }
}
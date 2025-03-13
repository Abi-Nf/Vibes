package com.vibes.rv

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vibes.rv.data.VibesDatabase
import com.vibes.rv.ui.MainAppUi

class MainActivity : ComponentActivity() {
    companion object {
        val permissions get(): Array<String> {
            val audioPermission =
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    Manifest.permission.READ_MEDIA_AUDIO
                else
                    Manifest.permission.READ_EXTERNAL_STORAGE

            return arrayOf(audioPermission)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissions(permissions, 0)
        val database = VibesDatabase.init(this)
        setContent {
            val viewModel = VibesViewModel(this.application, database)
            MainAppUi(viewModel)
        }
    }
}

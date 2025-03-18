package com.vibes.rv

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vibes.rv.MainActivity.Companion.permissions
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

    internal val requestActivityLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.all { it.value }
            if (allGranted) {
                onGrantedPermissions()
            } else {
                onDeniedPermissions()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissions()
    }
}

private fun MainActivity.onDeniedPermissions() {
    requestPermissions()
}

private fun MainActivity.onGrantedPermissions() {
    val database = VibesDatabase.init(this)
    setContent {
        val viewModel = viewModel { VibesViewModel(application, database) }
        MainAppUi(viewModel)
    }
}

private fun MainActivity.requestPermissions() {
    requestActivityLauncher.launch(permissions)
}

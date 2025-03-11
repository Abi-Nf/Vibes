package com.vibes.rv.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES_NAME = "settings"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

internal data object PreferenceKeys {
    val USER_THEME_MODE = intPreferencesKey("USER_THEME_MODE")
}

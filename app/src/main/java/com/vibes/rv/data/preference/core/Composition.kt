package com.vibes.rv.data.preference.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vibes.rv.data.preference.datastore
import com.vibes.rv.util.Conversion
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun <T> rememberPreference(
    key: Preferences.Key<T>,
    defaultValue: T,
): MutableState<T> {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by remember {
        context.datastore.data.map { it[key] ?: defaultValue }
    }.collectAsStateWithLifecycle(defaultValue)

    return remember(state) {
        object : MutableState<T> {
            override var value: T
                get() = state
                set(value) {
                    coroutineScope.launch {
                        context.datastore.edit {
                            it[key] = value
                        }
                    }
                }

            override fun component1() = value
            override fun component2(): (T) -> Unit = { value = it }
        }
    }
}

@Composable
fun <V, T> rememberPreference(
    key: Preferences.Key<T>,
    defaultValue: V,
    converter: Conversion<V, T>
): MutableState<V> {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by remember {
        context.datastore.data.map {
            it[key]?.let { converter.revert(it) } ?: defaultValue
        }
    }.collectAsStateWithLifecycle(defaultValue)

    return remember(state) {
        object : MutableState<V> {
            override var value: V
                get() = state
                set(value) {
                    coroutineScope.launch {
                        context.datastore.edit {
                            it[key] = converter.convert(value)
                        }
                    }
                }

            override fun component1() = value
            override fun component2(): (V) -> Unit = { value = it }
        }
    }
}
package com.vibes.rv.ui.layout.vibe_layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.screen.player.VibesPlayerView
import com.vibes.rv.ui.util.padding.times
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun BoxScope.VibeBar(
    viewModel: VibeLayoutViewModel,
    width: BarParam<Float>,
    playerHeight: BarParam<Dp>,
    roundness: BarParam<Dp>,
    inset: BarParam<PaddingValues>
) {
    val density = LocalDensity.current
    val config = LocalConfiguration.current

    val playerState by AppContext.playerState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val dragHeight = remember { Animatable(0f) }
    var status by remember { mutableStateOf(VibeBarState.NORMAL) }
    val dragState = rememberDraggableState { amount ->
        coroutineScope.launch {
            val value = dragHeight.value - (
                with(density) { amount.toDp().value } * 1.2f /
                with(config) { screenHeightDp }
            )
            dragHeight.snapTo(value.coerceIn(0f, 1f))
        }
    }

    val inverted by remember {
        derivedStateOf {
            (1f - (dragHeight.value * 3.2f)).coerceIn(0f, 1f)
        }
    }

    val contentHeight by remember(status) {
        derivedStateOf {
            if(status == VibeBarState.NORMAL) viewModel.contentCompactHeight else 0.dp
        }
    }

    val enabled by rememberUpdatedState(playerState.currentMedia != null)
    val playerHeight by remember {
        derivedStateOf {
            if(enabled) playerHeight.get(status) else 0.dp
        }
    }

    val height by animateDpAsState(
        playerHeight + contentHeight,
        spring(Spring.DampingRatioMediumBouncy),
        "bar-min-height-animation"
    )

    val barWidth by animateFloatAsState(
        if (dragHeight.value > 0f) 1f else width.get(status),
        spring(Spring.DampingRatioMediumBouncy),
        label = "bar-width"
    )

    fun setFloatHeight(value: Float) {
        coroutineScope.launch {
            dragHeight.animateTo(value, tween<Float>(400, 0, EaseInOut))
        }
    }

    fun setStatus(value: VibeBarState) {
        status = value
        setFloatHeight(
            when (value) {
                VibeBarState.FULLSCREEN -> 1f
                VibeBarState.NORMAL, VibeBarState.MINIFIED -> 0f
                else -> return
            }
        )
    }

    Box(
        Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .padding(inset.get(status) * inverted)
            .clip(RoundedCornerShape((roundness.get(status) * inverted).coerceAtLeast(0.dp)))
            .defaultMinSize(minHeight = height)
            .fillMaxHeight(dragHeight.value)
            .fillMaxWidth(barWidth)
            .combinedClickable(
                enabled = enabled,
                indication = null,
                interactionSource = null,
                onLongClick = {
                    val value =
                        if (status == VibeBarState.MINIFIED) VibeBarState.NORMAL
                        else VibeBarState.MINIFIED
                    setStatus(value)
                },
                onClick = {
                    if (status != VibeBarState.FULLSCREEN) {
                        val value =
                            if (status == VibeBarState.NORMAL) VibeBarState.FULLSCREEN
                            else VibeBarState.NORMAL
                        setStatus(value)
                    }
                }
            )
            .draggable(
                dragState,
                Orientation.Vertical,
                enabled = enabled,
                onDragStarted = { setStatus(VibeBarState.DRAG) },
                onDragStopped = { velocity ->
                    val fullscreen = velocity < 300f && dragHeight.value >= 0.3f
                    setStatus(
                        if (fullscreen) VibeBarState.FULLSCREEN
                        else VibeBarState.NORMAL
                    )
                }
            )
    ) {
        Column(
            Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val heightPercent by rememberUpdatedState(dragHeight.value)

            if(enabled) {
                VibesPlayerView(playerState, status, heightPercent)
            }

            val isVisible by remember {
                derivedStateOf {
                    heightPercent <= 0.2f && status != VibeBarState.MINIFIED
                }
            }
            Content(isVisible, viewModel.content)
        }
    }
}

@Composable
private fun Content(
    isVisible: Boolean,
    content: @Composable (() -> Unit)?
) {
    val transition = updateTransition(content != null && isVisible, "content-visibility")
    transition.AnimatedVisibility(
        { it },
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        content?.invoke()
    }
}
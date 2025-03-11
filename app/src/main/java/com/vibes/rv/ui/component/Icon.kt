package com.vibes.rv.ui.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Icon(
    vector: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
) = Icon(vector, vector.name, modifier, color)
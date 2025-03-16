package com.vibes.rv.ui.util.padding

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection

operator fun PaddingValues.times(value: Float): PaddingValues {
    val top = this.calculateTopPadding() * value
    val left = this.calculateLeftPadding(LayoutDirection.Ltr) * value
    val right = this.calculateRightPadding(LayoutDirection.Rtl) * value
    val bottom = this.calculateBottomPadding() * value
    return PaddingValues(
        top = top,
        start = left,
        end = right,
        bottom = bottom,
    )
}

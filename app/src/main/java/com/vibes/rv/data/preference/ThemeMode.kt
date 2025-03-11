package com.vibes.rv.data.preference

import com.vibes.rv.util.Conversion

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM;

    companion object : Conversion<ThemeMode, Int> {
        override fun convert(value: ThemeMode): Int {
            return when (value) {
                SYSTEM -> -1
                LIGHT -> 0
                DARK -> 1
            }
        }

        override fun revert(value: Int): ThemeMode {
            return when(value) {
                -1 -> SYSTEM
                0 -> LIGHT
                1 -> DARK
                else -> throw IllegalArgumentException("value $value is not convertible to ThemeMode")
            }
        }
    }
}
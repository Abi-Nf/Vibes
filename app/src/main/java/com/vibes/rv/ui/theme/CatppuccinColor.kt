package com.vibes.rv.ui.theme

import androidx.compose.ui.graphics.Color
import com.vibes.rv.ui.theme.catppuccin.CatppuccinOverlay
import com.vibes.rv.ui.theme.catppuccin.CatppuccinPalette
import com.vibes.rv.ui.theme.catppuccin.CatppuccinSurface
import com.vibes.rv.ui.theme.catppuccin.CatppuccinText

object CatppuccinColor {
    fun getPalette(isDark: Boolean): CatppuccinPalette {
        return if(isDark) MACHIATTO else LATTE
    }

    val LATTE = CatppuccinPalette(
        roseWater = Color(220, 138, 120),
        flamingo = Color(221, 120, 120),
        pink = Color(234, 118, 203),
        mauve = Color(136, 57, 239),
        red = Color(210, 15, 57),
        maroon = Color(230, 69, 83),
        peach = Color(254, 100, 11),
        yellow = Color(223, 142, 29),
        green = Color(64, 160, 43),
        teal = Color(23, 146, 153),
        sky = Color(32, 159, 181),
        sapphire = Color(32, 159, 181),
        blue = Color(30, 102, 245),
        lavender = Color(114, 135, 253),
        text = CatppuccinText(
            principal = Color.Black,
            main = Color(76, 79, 105),
            one = Color(108, 111, 133),
            two = Color(92, 95, 119),
            three = Color(124, 127, 147)
        ),
        overlay = CatppuccinOverlay(
            one = Color(156, 160, 176),
            two = Color(140, 143, 161),
            three = Color(124, 127, 147)
        ),
        surface = CatppuccinSurface(
            one = Color(204, 208, 218),
            two = Color(188, 192, 204),
            three = Color(172, 176, 190)
        ),
        base = Color(239, 241, 245),
        mantle = Color(230, 233, 239),
        crust = Color(220, 224, 232),
        principal = Color.White,
    )

    val MACHIATTO = CatppuccinPalette(
        roseWater = Color(245, 224, 220),
        flamingo = Color(242, 205, 205),
        pink = Color(245, 194, 231),
        mauve = Color(203, 166, 247),
        red = Color(243, 139, 168),
        maroon = Color(235, 160, 172),
        peach = Color(250, 179, 135),
        yellow = Color(249, 226, 175),
        green = Color(166, 227, 161),
        teal = Color(148, 226, 213),
        sky = Color(137, 220, 235),
        sapphire = Color(116, 199, 236),
        blue = Color(137, 180, 250),
        lavender = Color(180, 190, 254),
        text = CatppuccinText(
            principal = Color.White,
            main = Color(205, 214, 244),
            one = Color(166, 173, 200),
            two = Color(186, 194, 222),
            three = Color(147, 153, 178)
        ),
        overlay = CatppuccinOverlay(
            one = Color(108, 112, 134),
            two = Color(127, 132, 156),
            three = Color(147, 153, 178)
        ),
        surface = CatppuccinSurface(
            one = Color(49, 50, 68),
            two = Color(69, 71, 90),
            three = Color(88, 91, 112)
        ),
        base = Color(30, 30, 46),
        mantle = Color(24, 24, 37),
        crust = Color(17, 17, 27),
        principal = Color.Black,
    )
}
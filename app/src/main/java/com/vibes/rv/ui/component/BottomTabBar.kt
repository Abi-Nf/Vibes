package com.vibes.rv.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composables.icons.lucide.CircleUserRound
import com.composables.icons.lucide.DiscAlbum
import com.composables.icons.lucide.House
import com.composables.icons.lucide.ListMusic
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.UsersRound
import com.vibes.rv.ui.Destination
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.isActive

@Composable
fun BottomTabBar(controller: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val scope = BottomTabBarScope(this, controller)
        with(scope) {
            Navigable(Lucide.House, "Home", Destination.Tabbed.Home)
            Navigable(Lucide.DiscAlbum, "Albums", Destination.Tabbed.Albums)
            Navigable(Lucide.UsersRound, "Artists", Destination.Tabbed.Artists)
            Navigable(Lucide.ListMusic, "Playlists", Destination.Tabbed.Playlists)
            Navigable(Lucide.CircleUserRound, "Me", Destination.Tabbed.Me)
        }
    }
}

private data class BottomTabBarScope(
    private val parentScope: RowScope,
    private val controller: NavHostController
): RowScope {
    override fun Modifier.align(alignment: Alignment.Vertical): Modifier {
        return with(parentScope) { align(alignment) }
    }

    override fun Modifier.alignBy(alignmentLineBlock: (Measured) -> Int): Modifier {
        return with(parentScope) { alignBy(alignmentLineBlock) }
    }

    override fun Modifier.alignBy(alignmentLine: HorizontalAlignmentLine): Modifier {
        return with(parentScope) { alignBy(alignmentLine) }
    }

    override fun Modifier.alignByBaseline(): Modifier {
        return with(parentScope) { alignByBaseline() }
    }

    override fun Modifier.weight(
        weight: Float,
        fill: Boolean
    ): Modifier {
        return with(parentScope) { weight(weight, fill) }
    }

    @Composable
    fun <T: Destination> isActive(destination: T) = controller.isActive(destination)

    fun <T: Destination> navigate(destination: T) = controller.navigate(destination)
}

@Composable
private fun <T: Destination> BottomTabBarScope.Navigable(
    icon: ImageVector,
    label: String,
    destination: T
){
    val palette = AppContext.palette
    val isActive by isActive(destination)

    Column(
        Modifier
            .weight(1f)
            .clickable(interactionSource = null, indication = null) { navigate(destination) },
        Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        Alignment.CenterHorizontally
    ) {
        val color by animateColorAsState(
            if(isActive) palette.blue else palette.text.one,
            label = "nav-bar-button-color"
        )

        Icon(icon, color, Modifier.size(25.dp))

        val transition = updateTransition(isActive, "nav-bar-transition")
        transition.AnimatedVisibility({it}) {
            Text(
                text = label,
                color = color,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
package com.vibes.rv.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.vibes.rv.ui.component.Icon

@Composable
fun AppSettings() {
    Column(
        Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Vibe player",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 50.dp)
        )
        Text("Unstable version")
        Column(
            Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("૮(˶ㅠ︿ㅠ)ა", fontWeight = FontWeight.Bold, fontSize = 12.em)
            Text(
                "This area is still under construction"
            )
        }
    }
}
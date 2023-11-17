package com.durdinstudios.goonwarscollector.ui.components.spacers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.durdinstudios.goonwarscollector.ui.theme.Colors

@Composable
fun VerticalDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .fillMaxWidth(), color = Colors.appColorPalette.greyMedium
    )
}
package com.durdinstudios.goonwarscollector.ui.components.spacers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Suppress("UndocumentedPublicFunction")
@Composable
fun VerticalSpacer(modifier: Modifier = Modifier, height: Dp) {
    Spacer(modifier = modifier.height(height = height))
}

@Suppress("UndocumentedPublicFunction")
@Composable
fun HorizontalSpacer(modifier: Modifier = Modifier, width: Dp) {
    Spacer(modifier = modifier.width(width = width))
}

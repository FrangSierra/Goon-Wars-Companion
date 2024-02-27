package com.durdinstudios.goonwarscollector.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SingleTextAppTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    title: String,
    showOnBackArrowButton: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    actions: @Composable RowScope.() -> Unit = {},
) {
    val navigationIcon: @Composable (() -> Unit) = {
        ArrowBackIconButton(onClick = onBack, tint = contentColor)
    }

    InsetAwareTopAppBar(
        modifier = modifier,
        title = { Heading2(text = title) },
        navigationIcon = if (showOnBackArrowButton) navigationIcon else null,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        actions = actions
    )
}
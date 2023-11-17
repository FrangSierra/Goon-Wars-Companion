package com.durdinstudios.goonwarscollector.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import com.durdinstudios.goonwarscollector.ui.components.semantics.drawableResId

/**
 * Wrapper over [AppImage] that adds a [DrawableResId] semantics node so we can properly verify the set drawable resource in tests.
 */
@Composable
fun AppImage(@DrawableRes resId: Int,
             contentDescription: String?,
             modifier: Modifier = Modifier,
             alignment: Alignment = Alignment.Center,
             contentScale: ContentScale = ContentScale.Fit,
             alpha: Float = DefaultAlpha,
             tintColor: Color? = null,
             colorFilter: ColorFilter? = tintColor?.let { ColorFilter.tint(tintColor) }) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier.semantics { drawableResId = resId },
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}

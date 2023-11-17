package com.durdinstudios.goonwarscollector.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import com.durdinstudios.goonwarscollector.ui.components.semantics.drawableResId

/**
 * Wrapper over [Icon] that adds a [DrawableResId] semantics node so we can properly verify the set drawable resource in tests.
 */
@Composable
fun AppIcon(@DrawableRes resId: Int,
            contentDescription: String?,
            modifier: Modifier = Modifier,
            tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)) {
    Icon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier.semantics { drawableResId = resId },
        tint = tint
    )
}

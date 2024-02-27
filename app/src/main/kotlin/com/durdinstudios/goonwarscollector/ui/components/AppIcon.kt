package com.durdinstudios.goonwarscollector.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import com.durdinstudios.goonwarscollector.ui.components.semantics.drawableResId

/**
 * Wrapper over [Icon] that adds a [DrawableResId] semantics node so we can properly verify the set drawable resource in tests.
 */
@Composable
fun AppIcon(
    @DrawableRes resId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    Icon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier.semantics { drawableResId = resId },
        tint = tint
    )
}

@Composable
fun ArrowBackIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colors.onPrimary,
    removeButtonPadding: Boolean = false
) = GenericIconButton(modifier, iconModifier, onClick, tint, removeButtonPadding, Icons.Sharp.ArrowBack)

@Composable
fun SettingsIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colors.onPrimary,
    removeButtonPadding: Boolean = false
) = GenericIconButton(modifier, iconModifier, onClick, tint, removeButtonPadding, Icons.Sharp.Settings)

@Composable
fun ExpandedCheveronIcon(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colors.onPrimary,
    removeButtonPadding: Boolean = false
) = GenericIconButton(modifier, iconModifier, onClick, tint, removeButtonPadding, Icons.Sharp.KeyboardArrowDown)

@Composable
fun CollapsedCheveronIcon(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colors.onPrimary,
    removeButtonPadding: Boolean = false
) = GenericIconButton(modifier, iconModifier, onClick, tint, removeButtonPadding, Icons.Sharp.KeyboardArrowUp)

@Composable
fun AddIcon(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colors.onPrimary,
    removeButtonPadding: Boolean = false
) = GenericIconButton(modifier, iconModifier, onClick, tint, removeButtonPadding, Icons.Sharp.Add)


@Composable
fun GenericIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colors.onPrimary,
    removeButtonPadding: Boolean = false,
    imageVector: ImageVector,
) {
    if (!removeButtonPadding) {
        IconButton(onClick = onClick, modifier = modifier) {
            Icon(
                modifier = iconModifier,
                imageVector = imageVector,
                contentDescription = null,
                tint = tint
            )
        }
    } else {
        Icon(
            modifier = iconModifier.clickable { onClick() },
            imageVector = imageVector,
            contentDescription = null,
            tint = tint
        )
    }
}


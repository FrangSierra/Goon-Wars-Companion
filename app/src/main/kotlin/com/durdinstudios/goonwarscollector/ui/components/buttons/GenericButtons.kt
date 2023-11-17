package com.durdinstudios.goonwarscollector.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.ui.components.AppTextStyle

const val BUTTON_LOADING_PROGRESS_INDICATOR = "buttonLoadingProgressIndicator"

/**
 * Class that defines button style properties.
 */
data class GenericButtonStyle(
    val height: Dp = Dp.Unspecified,
    val shape: Shape = RectangleShape,
    val outlined: Boolean = false,
    val textStyle: AppTextStyle = AppTextStyle(),
    val backgroundColor: Color = Color.Unspecified,
    val pressedBackgroundColor: Color = Color.Unspecified,
    val focusedBackgroundColor: Color = Color.Unspecified,
    val disabledBackgroundColor: Color = Color.LightGray,
    val contentColor: Color = Color.Unspecified,
    val outlineColor: Color = contentColor,
    val disabledContentColor: Color = Color.Gray,
    val elevation: GenericButtonElevation = GenericButtonElevation()
) {
    /**
     * Class to define elevation depending on Button state.
     * Default values extracted from [ButtonDefaults.elevation()].
     */
    data class GenericButtonElevation(
        val defaultElevation: Dp = 2.dp,
        val pressedElevation: Dp = 8.dp,
        val disabledElevation: Dp = 0.dp
    ) {
        /**
         * Converts the elevation parameters into a [ButtonElevation]. Only usable in [Composable] functions.
         */
        @Composable
        fun toButtonElevation(): ButtonElevation = ButtonDefaults.elevation(
            defaultElevation = defaultElevation,
            pressedElevation = pressedElevation,
            disabledElevation = disabledElevation
        )
    }
}

/**
 * Implementation of a generic button that accepts some style parameters and a content
 * [Composable].
 */
@Suppress("LongMethod")
@Composable
internal fun GenericButton(
    modifier: Modifier = Modifier,
    style: GenericButtonStyle = GenericButtonStyle(),
    enabled: Boolean = true,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    // Focus requester to handle view focus
    val focusRequester = remember { FocusRequester() }
    // MutableInteractionSource to track changes of the component's interactions (like "focused")
    val interactionSource = remember { MutableInteractionSource() }

    val isFocused by interactionSource.collectIsFocusedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonBackgroundColor = when {
        forcePressed || isPressed -> style.pressedBackgroundColor
        forceFocused || isFocused -> style.focusedBackgroundColor
        else -> style.backgroundColor
    }

    // If we use heightIn, Compose enforces a minimum touch area size of 48 dp that also adds unwanted paddings
    // With LocalMinimumTouchTargetEnforcement we can disable this behaviour
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        if (style.outlined) {
            OutlinedButton(
                modifier = modifier
                    .heightIn(min = style.height)
                    .focusRequester(focusRequester)
                    .focusable(interactionSource = interactionSource),
                interactionSource = interactionSource,
                colors = outlinedButtonColors(
                    backgroundColor = buttonBackgroundColor,
                    contentColor = style.contentColor,
                    disabledContentColor = style.disabledContentColor
                ),
                elevation = style.elevation.toButtonElevation(),
                border = BorderStroke(1.dp, style.outlineColor),
                shape = style.shape,
                enabled = enabled,
                onClick = onClick,
                contentPadding = PaddingValues(
                    horizontal = 16.dp, // Default horizontal padding in ContentPadding
                    vertical = 0.dp
                )
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    content()
                }
            }
        } else {
            Button(
                modifier = modifier
                    .heightIn(min = style.height)
                    .focusRequester(focusRequester)
                    .focusable(interactionSource = interactionSource),
                interactionSource = interactionSource,
                colors = buttonColors(
                    backgroundColor = buttonBackgroundColor,
                    contentColor = style.contentColor,
                    disabledBackgroundColor = style.disabledBackgroundColor,
                    disabledContentColor = style.disabledContentColor
                ),
                elevation = style.elevation.toButtonElevation(),
                shape = style.shape,
                enabled = enabled,
                onClick = onClick,
                contentPadding = PaddingValues(
                    horizontal = 16.dp, // Default horizontal padding in ContentPadding
                    vertical = 0.dp
                )
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    content()
                }
            }
        }
    }
}

/**
 * Implementation of a generic loading button.
 */
@Composable
fun GenericLoadingButton(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    style: GenericButtonStyle = GenericButtonStyle(),
    enabled: Boolean = true,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
    loadingContent: @Composable () -> Unit = content
) {
    // Only set the onClick function if we're not in the "loading" state
    val onClickListener: () -> Unit = if (loading) {
        {}
    } else {
        onClick
    }

    GenericButton(modifier, style, enabled, forceFocused, forcePressed, onClickListener) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            if (loading) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    loadingContent()
                    if (displayContentWhenLoading) {
                        content()
                    }
                }
            } else {
                content()
            }
        }
    }
}

/**
 * Implementation of a generic button with a text and a loading state.
 */
@Composable
fun GenericLoadingButton(
    modifier: Modifier = Modifier,
    text: String = "",
    loading: Boolean = false,
    style: GenericButtonStyle = GenericButtonStyle(),
    enabled: Boolean = true,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        loading = loading,
        style = style,
        enabled = enabled,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick,
        loadingContent = {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .testTag(BUTTON_LOADING_PROGRESS_INDICATOR),
                color = LocalContentColor.current,
                strokeWidth = 2.dp
            )
        },
        content = {
            Text(
                text = text.let { if (style.textStyle.upperCase) it.uppercase() else it },
                style = style.textStyle.textStyle
                    .copy(
                        color = LocalContentColor.current,
                        textAlign = TextAlign.Center
                    )
            )
        }
    )
}

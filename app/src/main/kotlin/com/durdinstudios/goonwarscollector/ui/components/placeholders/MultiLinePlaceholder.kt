package com.durdinstudios.goonwarscollector.ui.components.placeholders

import androidx.compose.animation.core.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.shimmer
import kotlin.math.ceil

/**
 * Container for determinate the placeholder size and the line height
 *
 *  @param placeholderHeightPx individual placeholder height.
 *  @param lineHeightPx individual line height which takes into account the placeholder height and the spacing between lines.
 */
data class MultiLinePlaceholderOptions(
    val placeholderHeightPx: Float = -1F,
    val lineHeightPx: Float = -1F
) {
    companion object {

        /**
         * Retrieve a [MultiLinePlaceholderOptions] from a [TextStyle] making use
         * of the [TextStyle.fontSize] and [TextStyle.lineHeight] values
         */
        @Composable
        fun fromTextStyle(textStyle: TextStyle): MultiLinePlaceholderOptions {
            return MultiLinePlaceholderOptions(
                placeholderHeightPx = with(LocalDensity.current) { textStyle.fontSize.toPx() },
                lineHeightPx = with(LocalDensity.current) { textStyle.lineHeight.toPx() }
            )
        }

        /**
         * Retrieve a [MultiLinePlaceholderOptions] from a [TextStyle] making use
         * of the [TextStyle.fontSize] and [TextStyle.lineHeight] values
         */
        @Composable
        fun fromAnnotatedString(annotatedString: AnnotatedString): MultiLinePlaceholderOptions {
            val biggestHeightFontSizePx = with(LocalDensity.current) {
                annotatedString.spanStyles
                    .maxOfOrNull { it.item.fontSize.let { if (it.isSp) it.toPx() else 0f } }
                    ?: LocalTextStyle.current.fontSize.let { if (it.isSp) it.toPx() else 0f }
            }
            val biggestHeightLineHeightPx = with(LocalDensity.current) {
                annotatedString.paragraphStyles
                    .maxOfOrNull { it.item.lineHeight.let { if (it.isSp) it.toPx() else 0f } }
                    ?: LocalTextStyle.current.lineHeight.let { if (it.isSp) it.toPx() else 0f }
            }

            return MultiLinePlaceholderOptions(
                placeholderHeightPx = biggestHeightFontSizePx,
                lineHeightPx = biggestHeightLineHeightPx
            )
        }

        /**
         * Retrieve a [MultiLinePlaceholderOptions] from a the given [TextUnit] values.
         */
        @Composable
        fun from(placeholderHeight: TextUnit, lineHeight: TextUnit): MultiLinePlaceholderOptions {
            return MultiLinePlaceholderOptions(
                placeholderHeightPx = with(LocalDensity.current) { placeholderHeight.toPx() },
                lineHeightPx = with(LocalDensity.current) { lineHeight.toPx() }
            )
        }

        /**
         * Retrieve a [MultiLinePlaceholderOptions] from a the given [Dp] values.
         */
        @Composable
        fun from(placeholderHeight: Dp, lineHeight: Dp): MultiLinePlaceholderOptions {
            return MultiLinePlaceholderOptions(
                placeholderHeightPx = with(LocalDensity.current) { placeholderHeight.toPx() },
                lineHeightPx = with(LocalDensity.current) { lineHeight.toPx() }
            )
        }
    }
}

/**
 * Draws some skeleton UI which is typically used whilst content is 'loading'.
 *
 * Unlike [com.google.accompanist.placeholder.material.placeholder] this method allow to draw
 * multiple placeholders to simulate a more common placeholder under a long text. To make this
 * the method makes use of [MultiLinePlaceholderOptions] which can be obtained from different
 * Compose classes like [TextStyle] to determinate the placeholder and line height.
 *
 * You can provide a [PlaceholderHighlight] which runs an highlight animation on the placeholder.
 * The [shimmer] and [fade] implementations are provided for easy usage.
 *
 * A cross-fade transition will be applied to the content and placeholder UI when the [visible]
 * value changes. The transition can be customized via the [contentFadeTransitionSpec] and
 * [placeholderFadeTransitionSpec] parameters.
 *
 * You can find more information on the pattern at the Material Theming
 * [Placeholder UI](https://material.io/design/communication/launch-screen.html#placeholder-ui)
 * guidelines.
 *
 * @sample TextFieldPlaceholderPreview
 *
 * @param visible whether the placeholder should be visible or not.
 * @param color the color used to draw the placeholder UI.
 * @param shape desired shape of the placeholder. Defaults to [RectangleShape].
 * @param multiLinePlaceholderOptions options which determine how the multiple placeholders are drawn.
 * @param highlight optional highlight animation.
 * @param placeholderFadeTransitionSpec The transition spec to use when fading the placeholder
 * on/off screen. The boolean parameter defined for the transition is [visible].
 * @param contentFadeTransitionSpec The transition spec to use when fading the content
 * on/off screen. The boolean parameter defined for the transition is [visible].
 */
@Suppress("LongParameterList", "LongMethod")
fun Modifier.multiLinePlaceholder(
    visible: Boolean,
    color: Color,
    shape: Shape = RectangleShape,
    multiLinePlaceholderOptions: MultiLinePlaceholderOptions = MultiLinePlaceholderOptions(),
    highlight: PlaceholderHighlight? = null,
    placeholderFadeTransitionSpec: @Composable Transition.Segment<Boolean>.() -> FiniteAnimationSpec<Float> = { spring() },
    contentFadeTransitionSpec: @Composable Transition.Segment<Boolean>.() -> FiniteAnimationSpec<Float> = { spring() }
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "placeholder"
        value = visible
        properties["visible"] = visible
        properties["color"] = color
        properties["highlight"] = highlight
        properties["shape"] = shape
    }
) {
    // Values used for caching purposes
    val lastSize = remember { Ref<Size>() }
    val lastLayoutDirection = remember { Ref<LayoutDirection>() }
    val lastOutline = remember { Ref<Outline>() }

    // The current highlight animation progress
    var highlightProgress: Float by remember { mutableStateOf(0f) }

    // This is our crossfade transition
    val transitionState = remember { MutableTransitionState(visible) }.apply {
        targetState = visible
    }
    val transition = updateTransition(transitionState, "placeholder_crossfade")

    val placeholderAlpha by transition.animateFloat(
        transitionSpec = placeholderFadeTransitionSpec,
        label = "placeholder_fade",
        targetValueByState = { placeholderVisible -> if (placeholderVisible) 1f else 0f }
    )
    val contentAlpha by transition.animateFloat(
        transitionSpec = contentFadeTransitionSpec,
        label = "content_fade",
        targetValueByState = { placeholderVisible -> if (placeholderVisible) 0f else 1f }
    )

    // Run the optional animation spec and update the progress if the placeholder is visible
    val animationSpec = highlight?.animationSpec
    if (animationSpec != null && (visible || placeholderAlpha >= 0.01f)) {
        val infiniteTransition = rememberInfiniteTransition()
        highlightProgress = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = animationSpec
        ).value
    }

    val paint = remember { Paint() }
    remember(color, shape, highlight) {
        drawWithContent {
            // Draw the composable content first
            if (contentAlpha in 0.01f..0.99f) {
                // If the content alpha is between 1% and 99%, draw it in a layer with
                // the alpha applied
                paint.alpha = contentAlpha
                withLayer(paint) {
                    with(this@drawWithContent) {
                        drawContent()
                    }
                }
            } else if (contentAlpha >= 0.99f) {
                // If the content alpha is > 99%, draw it with no alpha
                drawContent()
            }

            if (placeholderAlpha in 0.01f..0.99f) {
                // If the placeholder alpha is between 1% and 99%, draw it in a layer with
                // the alpha applied
                paint.alpha = placeholderAlpha
                withLayer(paint) {
                    drawMultiplePlaceholders(
                        shape = shape,
                        color = color,
                        highlight = highlight,
                        progress = highlightProgress,
                        lastOutline = lastOutline.value,
                        lastLayoutDirection = lastLayoutDirection.value,
                        lastSize = lastSize.value,
                        multiLinePlaceholderOptions = multiLinePlaceholderOptions
                    )
                }
            } else if (placeholderAlpha >= 0.99f) {
                // If the placeholder alpha is > 99%, draw it with no alpha
                drawMultiplePlaceholders(
                    shape = shape,
                    color = color,
                    highlight = highlight,
                    progress = highlightProgress,
                    lastOutline = lastOutline.value,
                    lastLayoutDirection = lastLayoutDirection.value,
                    lastSize = lastSize.value,
                    multiLinePlaceholderOptions = multiLinePlaceholderOptions
                )
            }

            // Keep track of the last size & layout direction
            lastSize.value = size
            lastLayoutDirection.value = layoutDirection
        }
    }
}

@Suppress("LongParameterList", "LongMethod")
private fun DrawScope.drawMultiplePlaceholders(
    shape: Shape,
    color: Color,
    highlight: PlaceholderHighlight?,
    progress: Float,
    lastOutline: Outline?,
    lastLayoutDirection: LayoutDirection?,
    lastSize: Size?,
    multiLinePlaceholderOptions: MultiLinePlaceholderOptions = MultiLinePlaceholderOptions()
): Outline? {
    // retrieve the desired heights from the options
    val placeholderHeight = multiLinePlaceholderOptions.placeholderHeightPx
    val lineHeight = multiLinePlaceholderOptions.lineHeightPx

    // calculate the expected lines based on the view height and the placeholder height
    val expectedLines = ceil(size.height / lineHeight).toInt()
    val lineSize = Size(size.width, placeholderHeight)

    // shortcut to avoid Outline calculation and allocation
    if (shape === RectangleShape) {
        // Draw the initial background color
        repeat(expectedLines) {
            translate(top = lineHeight * it, left = 0F) {
                drawRect(color = color, topLeft = Offset.Zero, size = lineSize)

                if (highlight != null) {
                    drawRect(
                        size = lineSize,
                        brush = highlight.brush(progress, lineSize),
                        alpha = highlight.alpha(progress)
                    )
                }
            }
        }

        // We didn't create an outline so return null
        return null
    }

    // Otherwise we need to create an outline from the shape
    val outline = lastOutline.takeIf {
        lineSize == lastSize && layoutDirection == lastLayoutDirection
    } ?: shape.createOutline(lineSize, layoutDirection, this)

    repeat(expectedLines) {
        translate(top = lineHeight * it, left = 0F) {
            // Draw the placeholder color
            drawOutline(outline = outline, color = color)

            if (highlight != null) {
                drawOutline(
                    outline = outline,
                    brush = highlight.brush(progress, lineSize),
                    alpha = highlight.alpha(progress)
                )
            }
        }
    }

    // Return the outline we used
    return outline
}

private inline fun DrawScope.withLayer(
    paint: Paint,
    drawBlock: DrawScope.() -> Unit
) = drawIntoCanvas { canvas ->
    canvas.saveLayer(size.toRect(), paint)
    drawBlock()
    canvas.restore()
}

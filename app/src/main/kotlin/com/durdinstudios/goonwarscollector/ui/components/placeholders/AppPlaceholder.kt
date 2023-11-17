package com.durdinstudios.goonwarscollector.ui.components.placeholders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.theme.Colors
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import com.minikorp.duo.Task

/**
 * Local loading [Boolean] param to track the state of each screen across the composition hierarchy.
 *
 * WARNING: This value MUST be used together with [withLocalLoadingState] together with a [MutableState] of a [Boolean] to receive
 * the proper updates when the state gets change and trigger any needed recomposition based on these values.
 *
 * See example:
 *
 *  val isLoadingState = pepeStore.flow()
 *                                .select { it.task.isLoading || it.task.isEmpty }
 *                                .collectAsState(initial = true)
 *
 *  withLocalLoadingState(isLoadingState) {
 *        Button(
 *        text = "Potato",
 *        modifier = Modifier.clickable(enabled = LocalLoadingState.current
 *        )
 *  }
 */
val LocalLoadingState = compositionLocalOf { false }

/**
 * Method to provide the actual value of the [LocalLoadingState] for the whole content of the given composable.
 *
 * @param isLoading - actual value for [LocalLoadingState]. Keep in mind that if you want this value to get updated
 * and trigger recomposition you must provide a [MutableState] of a [Boolean].
 */
@Composable
fun withLocalLoadingState(isLoading: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLoadingState provides isLoading) {
        content()
    }
}

/**
 * Method to provide the actual value of the [LocalLoadingState] for the whole content of the given composable.
 *
 * @param resource - A [Resource] to check to compute the loading state.
 */
@Composable
fun withLocalLoadingState(resource: Task, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLoadingState provides (resource.isRunning)) {
        content()
    }
}

/**
 * Custom placeholder [Modifier] that makes uses of the actual [LocalLoadingState] value to make the placeholder visible.
 * This allow us to encapsulate the logic regarding the placeholder and the loading state of our screens without the need of passing
 * a `isLoading` param across all the composable methods from the hierarchy.
 *
 * Include a [MultiLinePlaceholderOptions] if you want to split the
 */
fun Modifier.withPlaceholder(
    force: Boolean = false,
    shape: Shape = RectangleShape,
    color: Color = Color.Black,
    highlight: Color = Colors.appColorPalette.surface

    ,
    multiLineOptions: MultiLinePlaceholderOptions? = null
) = composed {
    if (multiLineOptions != null) {
        this.multiLinePlaceholder(
            visible = LocalLoadingState.current || force,
            color = color,
            shape = shape,
            highlight = PlaceholderHighlight.shimmer(highlight),
            multiLinePlaceholderOptions = multiLineOptions
        )
    } else {
        this.placeholder(
            visible = LocalLoadingState.current || force,
            color = color,
            shape = shape,
            highlight = PlaceholderHighlight.shimmer(highlight)
        )
    }
}

/**
 * [Modifier] that makes uses of the actual [LocalLoadingState] value to prevent the [onClick] to get called when the state is loading.
 */
fun Modifier.clickablePlaceholder(onClick: () -> Unit) = composed {
    this.clickable(enabled = !LocalLoadingState.current, onClick = onClick)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RoundedMultiLinePlaceholder() {
    MultiLinePlaceholder(RoundedCornerShape(4.dp))
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun MultiLinePlaceholder(
    shape: Shape = RectangleShape,
    multiLineOptions: MultiLinePlaceholderOptions = MultiLinePlaceholderOptions.from(16.sp, 24.sp)
) {
    PlaceholderPreview(true, shape, multiLineOptions)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RoundedPlaceholder() {
    PlaceholderPreview(shape = RoundedCornerShape(4.dp))
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PlaceholderPreview(
    isLoading: Boolean = true,
    shape: Shape = RectangleShape,
    multiLineOptions: MultiLinePlaceholderOptions? = null
) {
    AppPreview(isLoading = true) {
        Box(Modifier.padding(16.dp)) {
            Text(
                text = "There was an error",
                modifier = Modifier.withPlaceholder(shape = shape, multiLineOptions = multiLineOptions),
                style = MaterialTheme.typography.h5
            )
        }
    }
}

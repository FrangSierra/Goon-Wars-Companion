package com.durdinstudios.goonwarscollector.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.ui.components.AppPreview
import com.durdinstudios.goonwarscollector.ui.components.placeholders.LocalLoadingState

/**
 * All the components here use LocalLoadingState to infer loading states.
 */

@Composable
fun ButtonSolidPrimarySmall(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: (() -> Unit) = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidPrimarySmallStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidPrimaryMedium(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidPrimaryMediumStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidPrimaryLarge(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidPrimaryLargeStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidSecondarySmall(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidSecondarySmallStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidSecondaryMedium(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidSecondaryMediumStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidSecondaryLarge(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidSecondaryLargeStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonOutlineDefaultSmall(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonOutlineDefaultSmallStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonOutlineDefaultMedium(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonOutlineDefaultMediumStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonOutlineDefaultLarge(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonOutlineDefaultLargeStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonOutlineAlertSmall(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonOutlineAlertSmallStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonOutlineAlertMedium(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonOutlineAlertMediumStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonOutlineAlertLarge(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonOutlineAlertLargeStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidSurfaceSmall(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidSurfaceSmallStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidSurfaceMedium(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidSurfaceMediumStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Composable
fun ButtonSolidSurfaceLarge(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    loading: Boolean? = null,
    forceFocused: Boolean = false,
    forcePressed: Boolean = false,
    displayContentWhenLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    GenericLoadingButton(
        modifier = modifier,
        text = text,
        style = buttonSolidSurfaceLargeStyle,
        enabled = enabled,
        loading = loading ?: LocalLoadingState.current,
        forceFocused = forceFocused,
        forcePressed = forcePressed,
        displayContentWhenLoading = displayContentWhenLoading,
        onClick = onClick
    )
}

@Preview(showBackground = true, backgroundColor = 0xfff0f0f0)
@Composable
fun ButtonSolidPrimaryPreview() {
    val primarySmallList = listOf<@Composable () -> Unit>(
        { ButtonSolidPrimarySmall(text = "Normal") },
        { ButtonSolidPrimarySmall(text = "Focused", forceFocused = true) },
        { ButtonSolidPrimarySmall(text = "Pressed", forcePressed = true) },
        { ButtonSolidPrimarySmall(text = "Disabled", enabled = false) },
        { ButtonSolidPrimarySmall(text = "Loading", loading = true) }
    )

    val primaryMediumList = listOf<@Composable () -> Unit>(
        { ButtonSolidPrimaryMedium(text = "Normal") },
        { ButtonSolidPrimaryMedium(text = "Focused", forceFocused = true) },
        { ButtonSolidPrimaryMedium(text = "Pressed", forcePressed = true) },
        { ButtonSolidPrimaryMedium(text = "Disabled", enabled = false) },
        { ButtonSolidPrimaryMedium(text = "Loading", loading = true) }
    )

    val primaryLargeList = listOf<@Composable () -> Unit>(
        { ButtonSolidPrimaryLarge(text = "Normal") },
        { ButtonSolidPrimaryLarge(text = "Focused", forceFocused = true) },
        { ButtonSolidPrimaryLarge(text = "Pressed", forcePressed = true) },
        { ButtonSolidPrimaryLarge(text = "Disabled", enabled = false) },
        { ButtonSolidPrimaryLarge(text = "Loading", loading = true) }
    )

    AppPreview {
        Column {
            Row {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Small")
                    }
                    items(primarySmallList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Medium")
                    }
                    items(primaryMediumList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Large")
                    }
                    items(primaryLargeList) { item ->
                        item()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff0f0f0)
@Composable
fun ButtonSolidSecondaryPreview() {
    val secondarySmallList = listOf<@Composable () -> Unit>(
        { ButtonSolidSecondarySmall(text = "Normal") },
        { ButtonSolidSecondarySmall(text = "Focused", forceFocused = true) },
        { ButtonSolidSecondarySmall(text = "Pressed", forcePressed = true) },
        { ButtonSolidSecondarySmall(text = "Disabled", enabled = false) },
        { ButtonSolidSecondarySmall(text = "Loading", loading = true) }
    )

    val secondaryMediumList = listOf<@Composable () -> Unit>(
        { ButtonSolidSecondaryMedium(text = "Normal") },
        { ButtonSolidSecondaryMedium(text = "Focused", forceFocused = true) },
        { ButtonSolidSecondaryMedium(text = "Pressed", forcePressed = true) },
        { ButtonSolidSecondaryMedium(text = "Disabled", enabled = false) },
        { ButtonSolidSecondaryMedium(text = "Loading", loading = true) }
    )

    val secondaryLargeList = listOf<@Composable () -> Unit>(
        { ButtonSolidSecondaryLarge(text = "Normal") },
        { ButtonSolidSecondaryLarge(text = "Focused", forceFocused = true) },
        { ButtonSolidSecondaryLarge(text = "Pressed", forcePressed = true) },
        { ButtonSolidSecondaryLarge(text = "Disabled", enabled = false) },
        { ButtonSolidSecondaryLarge(text = "Loading", loading = true) }
    )

    AppPreview {
        Column {
            Row {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Small")
                    }
                    items(secondarySmallList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Medium")
                    }
                    items(secondaryMediumList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Large")
                    }
                    items(secondaryLargeList) { item ->
                        item()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff0f0f0)
@Composable
fun ButtonOutlineDefaultPreview() {
    val outlineDefaultSmallList = listOf<@Composable () -> Unit>(
        { ButtonOutlineDefaultSmall(text = "Normal") },
        { ButtonOutlineDefaultSmall(text = "Focused", forceFocused = true) },
        { ButtonOutlineDefaultSmall(text = "Pressed", forcePressed = true) },
        { ButtonOutlineDefaultSmall(text = "Disabled", enabled = false) },
        { ButtonOutlineDefaultSmall(text = "Loading", loading = true) }
    )

    val outlineDefaultMediumList = listOf<@Composable () -> Unit>(
        { ButtonOutlineDefaultMedium(text = "Normal") },
        { ButtonOutlineDefaultMedium(text = "Focused", forceFocused = true) },
        { ButtonOutlineDefaultMedium(text = "Pressed", forcePressed = true) },
        { ButtonOutlineDefaultMedium(text = "Disabled", enabled = false) },
        { ButtonOutlineDefaultMedium(text = "Loading", loading = true) }
    )

    val outlineDefaultLargeList = listOf<@Composable () -> Unit>(
        { ButtonOutlineDefaultLarge(text = "Normal") },
        { ButtonOutlineDefaultLarge(text = "Focused", forceFocused = true) },
        { ButtonOutlineDefaultLarge(text = "Pressed", forcePressed = true) },
        { ButtonOutlineDefaultLarge(text = "Disabled", enabled = false) },
        { ButtonOutlineDefaultLarge(text = "Loading", loading = true) }
    )

    AppPreview {
        Column {
            Row {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Small")
                    }
                    items(outlineDefaultSmallList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Medium")
                    }
                    items(outlineDefaultMediumList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Large")
                    }
                    items(outlineDefaultLargeList) { item ->
                        item()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff0f0f0)
@Composable
fun ButtonOutlineAlertPreview() {
    val outlineAlertSmallList = listOf<@Composable () -> Unit>(
        { ButtonOutlineAlertSmall(text = "Normal") },
        { ButtonOutlineAlertSmall(text = "Focused", forceFocused = true) },
        { ButtonOutlineAlertSmall(text = "Pressed", forcePressed = true) },
        { ButtonOutlineAlertSmall(text = "Disabled", enabled = false) },
        { ButtonOutlineAlertSmall(text = "Loading", loading = true) }
    )

    val outlineAlertMediumList = listOf<@Composable () -> Unit>(
        { ButtonOutlineAlertMedium(text = "Normal") },
        { ButtonOutlineAlertMedium(text = "Focused", forceFocused = true) },
        { ButtonOutlineAlertMedium(text = "Pressed", forcePressed = true) },
        { ButtonOutlineAlertMedium(text = "Disabled", enabled = false) },
        { ButtonOutlineAlertMedium(text = "Loading", loading = true) }
    )

    val outlineAlertLargeList = listOf<@Composable () -> Unit>(
        { ButtonOutlineAlertLarge(text = "Normal") },
        { ButtonOutlineAlertLarge(text = "Focused", forceFocused = true) },
        { ButtonOutlineAlertLarge(text = "Pressed", forcePressed = true) },
        { ButtonOutlineAlertLarge(text = "Disabled", enabled = false) },
        { ButtonOutlineAlertLarge(text = "Loading", loading = true) }
    )

    AppPreview {
        Column {
            Row {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Small")
                    }
                    items(outlineAlertSmallList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Medium")
                    }
                    items(outlineAlertMediumList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Large")
                    }
                    items(outlineAlertLargeList) { item ->
                        item()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff0f0f0)
@Composable
fun ButtonSolidSurfaceDefaultPreview() {
    val outlineDefaultSmallList = listOf<@Composable () -> Unit>(
        { ButtonSolidSurfaceSmall(text = "Normal") },
        { ButtonSolidSurfaceSmall(text = "Focused", forceFocused = true) },
        { ButtonSolidSurfaceSmall(text = "Pressed", forcePressed = true) },
        { ButtonSolidSurfaceSmall(text = "Disabled", enabled = false) },
        { ButtonSolidSurfaceSmall(text = "Loading", loading = true) }
    )

    val outlineDefaultMediumList = listOf<@Composable () -> Unit>(
        { ButtonSolidSurfaceMedium(text = "Normal") },
        { ButtonSolidSurfaceMedium(text = "Focused", forceFocused = true) },
        { ButtonSolidSurfaceMedium(text = "Pressed", forcePressed = true) },
        { ButtonSolidSurfaceMedium(text = "Disabled", enabled = false) },
        { ButtonSolidSurfaceMedium(text = "Loading", loading = true) }
    )

    val outlineDefaultLargeList = listOf<@Composable () -> Unit>(
        { ButtonSolidSurfaceLarge(text = "Normal") },
        { ButtonSolidSurfaceLarge(text = "Focused", forceFocused = true) },
        { ButtonSolidSurfaceLarge(text = "Pressed", forcePressed = true) },
        { ButtonSolidSurfaceLarge(text = "Disabled", enabled = false) },
        { ButtonSolidSurfaceLarge(text = "Loading", loading = true) }
    )

    AppPreview {
        Column {
            Row {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Small")
                    }
                    items(outlineDefaultSmallList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Medium")
                    }
                    items(outlineDefaultMediumList) { item ->
                        item()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text(text = "Large")
                    }
                    items(outlineDefaultLargeList) { item ->
                        item()
                    }
                }
            }
        }
    }
}

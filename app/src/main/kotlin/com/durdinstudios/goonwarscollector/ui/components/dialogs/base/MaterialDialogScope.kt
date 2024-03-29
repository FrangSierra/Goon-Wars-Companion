package com.durdinstudios.goonwarscollector.ui.components.dialogs.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.efimeral.continentalapp.ui.components.dialogs.base.isLargeDevice
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min

const val MATERIAL_DIALOG = "materialDialog"

/**
 *  Interface defining values and functions which are available to any code
 *  within a [MaterialDialog]'s content parameter
 */
interface MaterialDialogScope {
    val dialogState: MaterialDialogState
    val dialogButtons: MaterialDialogButtons

    val callbacks: SnapshotStateMap<Int, () -> Unit>
    val positiveButtonEnabled: SnapshotStateMap<Int, Boolean>

    val autoDismiss: Boolean

    /**
     * Hides the dialog and calls any callbacks from components in the dialog
     */
    fun submit()

    /**
     * Clears the dialog's state
     */
    fun reset()

    /**
     * Adds a value to the [positiveButtonEnabled] map and updates the value in the map when
     * [valid] changes
     *
     * @param valid boolean value to initialise the index in the list
     * @param onDispose cleanup callback when component calling this gets destroyed
     */
    @Composable
    fun PositiveButtonEnabled(valid: Boolean, onDispose: () -> Unit)

    /**
     * Adds a callback to the dialog which is called on positive button press
     *
     * @param callback called when positive button is pressed
     */
    @Composable
    fun DialogCallback(callback: () -> Unit)
}

internal class MaterialDialogScopeImpl(
    override val dialogState: MaterialDialogState,
    override val autoDismiss: Boolean = true
) : MaterialDialogScope {
    override val dialogButtons = MaterialDialogButtons(this)

    override val callbacks = mutableStateMapOf<Int, () -> Unit>()
    private val callbackCounter = AtomicInteger(0)

    override val positiveButtonEnabled = mutableStateMapOf<Int, Boolean>()
    private val positiveEnabledCounter = AtomicInteger(0)

    /**
     * Hides the dialog and calls any callbacks from components in the dialog
     */
    override fun submit() {
        dialogState.hide()
        callbacks.values.forEach {
            it()
        }
    }

    /**
     * Clears the dialog callbacks and positive button enables values along with their
     * respective counters
     */
    override fun reset() {
        positiveButtonEnabled.clear()
        callbacks.clear()

        positiveEnabledCounter.set(0)
        callbackCounter.set(0)
    }

    /**
     * Adds a value to the [positiveButtonEnabled] map and updates the value in the map when
     * [valid] changes
     *
     * @param valid boolean value to initialise the index in the list
     * @param onDispose cleanup callback when component calling this gets destroyed
     */
    @Composable
    override fun PositiveButtonEnabled(valid: Boolean, onDispose: () -> Unit) {
        val positiveEnabledIndex = remember { positiveEnabledCounter.getAndIncrement() }

        DisposableEffect(valid) {
            positiveButtonEnabled[positiveEnabledIndex] = valid
            onDispose {
                positiveButtonEnabled[positiveEnabledIndex] = true
                onDispose()
            }
        }
    }

    /**
     * Adds a callback to the dialog which is called on positive button press
     *
     * @param callback called when positive button is pressed
     */
    @Composable
    override fun DialogCallback(callback: () -> Unit) {
        val callbackIndex = rememberSaveable { callbackCounter.getAndIncrement() }

        DisposableEffect(Unit) {
            callbacks[callbackIndex] = callback
            onDispose { callbacks[callbackIndex] = {} }
        }
    }
}

/**
 *  The [MaterialDialogState] class is used to store the state for a [MaterialDialog]
 *
 * @param initialValue the initial showing state of the dialog
 */
class MaterialDialogState(initialValue: Boolean = false) {
    var showing by mutableStateOf(initialValue)

    /**
     *  Dialog background color with elevation overlay
     */
    var dialogBackgroundColor by mutableStateOf<Color?>(null)

    /**
     *  Shows the dialog
     */
    fun show() {
        showing = true
    }

    /**
     * Clears focus with a given [FocusManager] and then hides the dialog
     *
     * @param focusManager the focus manager of the dialog view
     */
    fun hide(focusManager: FocusManager? = null) {
        focusManager?.clearFocus()
        showing = false
    }

    companion object {
        /**
         * The default [Saver] implementation for [MaterialDialogState].
         */
        @SuppressWarnings("FunctionNaming")
        fun Saver(): Saver<MaterialDialogState, *> = Saver(
            save = { it.showing },
            restore = { MaterialDialogState(it) }
        )
    }
}

/**
 * Create and [remember] a [MaterialDialogState].
 *
 * @param initialValue the initial showing state of the dialog
 */
@Composable
fun rememberMaterialDialogState(initialValue: Boolean = false): MaterialDialogState {
    return rememberSaveable(saver = MaterialDialogState.Saver()) {
        MaterialDialogState(initialValue)
    }
}

private val MATERIAL_DIALOG_DEFAULT_MAX_SIZE = 560.dp
val MATERIAL_DIALOG_MAX_SIZE_UNSPECIFIED = (-1).dp

/**
 *  Builds a dialog with the given content
 * @param dialogState state of the dialog
 * @param backgroundColor background color of the dialog
 * @param shape shape of the dialog and components used in the dialog
 * @param border border stoke of the dialog
 * @param elevation elevation of the dialog
 * @param maxDefaultWidth Override maximum default width of the dialog. You must set [DialogProperties.usePlatformDefaultWidth] to false
 *                         in order for this to work as expected
 * @param autoDismissOnButtonClick when true the dialog is hidden on any button press
 * @param onCloseRequest function to be executed when user clicks outside dialog
 * @param buttons the buttons layout of the dialog
 * @param content the body content of the dialog
 */
@SuppressWarnings("LongMethod")
@Composable
fun MaterialDialog(
    dialogState: MaterialDialogState = rememberMaterialDialogState(),
    backgroundColor: Color = MaterialTheme.colors.background,
    shape: Shape = MaterialTheme.shapes.medium,
    border: BorderStroke? = null,
    elevation: Dp = 24.dp,
    maxDefaultWidth: Dp = MATERIAL_DIALOG_MAX_SIZE_UNSPECIFIED,
    autoDismissOnButtonClick: Boolean = true,
    properties: DialogProperties = DialogProperties(),
    onCloseRequest: (MaterialDialogState) -> Unit = { it.hide() },
    buttons: @Composable MaterialDialogButtons.() -> Unit = {},
    content: @Composable MaterialDialogScope.() -> Unit
) {
    check(maxDefaultWidth == MATERIAL_DIALOG_MAX_SIZE_UNSPECIFIED || !properties.usePlatformDefaultWidth) {
        "You must set properties.usePlatformDefaultWidth to false to make maxDefaultWidth work: " +
        "maxDefaultWidth = $maxDefaultWidth; usePlatformDefaultWidth = ${properties.usePlatformDefaultWidth}"
    }

    val dialogScope = remember { MaterialDialogScopeImpl(dialogState, autoDismissOnButtonClick) }

    DisposableEffect(dialogState.showing) {
        if (!dialogState.showing) dialogScope.reset()
        onDispose { }
    }

    BoxWithConstraints {
        val maxHeight = if (isLargeDevice()) {
            LocalConfiguration.current.screenHeightDp.dp - 90.dp
        } else {
            MATERIAL_DIALOG_DEFAULT_MAX_SIZE
        }

        val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx().toInt() }
        val isDialogFullWidth = LocalConfiguration.current.screenWidthDp.dp == maxWidth
        val padding = if (isDialogFullWidth) 16.dp else 0.dp

        if (dialogState.showing) {
            dialogState.dialogBackgroundColor = LocalElevationOverlay.current?.apply(
                color = backgroundColor,
                elevation = elevation
            ) ?: MaterialTheme.colors.surface

            Dialog(properties = properties, onDismissRequest = { onCloseRequest(dialogState) }) {
                val maxSurfaceWidth = if (maxDefaultWidth == MATERIAL_DIALOG_MAX_SIZE_UNSPECIFIED) {
                    MATERIAL_DIALOG_DEFAULT_MAX_SIZE
                } else {
                    maxDefaultWidth
                }

                Surface(
                    modifier = Modifier
                        .sizeIn(maxHeight = maxHeight, maxWidth = maxSurfaceWidth)
                        .fillMaxWidth()
                        .padding(horizontal = padding)
                        .clipToBounds()
                        .wrapContentHeight()
                        .testTag(MATERIAL_DIALOG),
                    shape = shape,
                    color = backgroundColor,
                    border = border,
                    elevation = elevation
                ) {
                    Layout(
                        content = {
                            dialogScope.DialogButtonsLayout(
                                modifier = Modifier.layoutId("buttons"),
                                content = buttons
                            )
                            Column(Modifier.layoutId("content")) { content(dialogScope) }
                        }
                    ) { measurables, constraints ->
                        val buttonsHeight =
                            measurables[0].minIntrinsicHeight(constraints.maxWidth)
                        val buttonsPlaceable = measurables[0].measure(
                            constraints.copy(maxHeight = buttonsHeight, minHeight = 0)
                        )

                        val contentPlaceable = measurables[1].measure(
                            constraints.copy(
                                maxHeight = maxHeightPx - buttonsPlaceable.height,
                                minHeight = 0
                            )
                        )

                        val height =
                            min(maxHeightPx, buttonsPlaceable.height + contentPlaceable.height)

                        return@Layout layout(constraints.maxWidth, height) {
                            contentPlaceable.place(0, 0)
                            buttonsPlaceable.place(0, height - buttonsPlaceable.height)
                        }
                    }
                }
            }
        }
    }
}

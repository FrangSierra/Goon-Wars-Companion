package com.durdinstudios.goonwarscollector.ui.components.dialogs.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.durdinstudios.goonwarscollector.ui.components.spacers.HorizontalSpacer

private val bottomPadding = Modifier.padding(bottom = 8.dp)

/**
 * Adds a selectable list with custom items to the dialog
 *
 * @param list list of given generic type
 * @param onClick callback with the index and item when a list object is clicked
 * @param isEnabled a function to check if the item at a given index is enabled/clickable
 * @param item a composable function which takes an object of given generic type
 */
@SuppressWarnings("ExplicitItLambdaParameter")
@Composable
fun <T> MaterialDialogScope.listItems(
    list: List<T>,
    closeOnClick: Boolean = true,
    onClick: (index: Int, item: T) -> Unit = { _, _ -> },
    isEnabled: (index: Int) -> Boolean = { _ -> true },
    item: @Composable (index: Int, T) -> Unit
) {
    BoxWithConstraints {
        LazyColumn(
            Modifier
                .then(bottomPadding)
                .testTag("dialogList")
        ) {
            itemsIndexed(list) { index, it ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .testTag("dialogListItem$index")
                        .clickable(
                            onClick = {
                                if (closeOnClick) {
                                    dialogState.hide()
                                }
                                onClick(index, it)
                            },
                            enabled = isEnabled(index)
                        )
                        .padding(horizontal = 24.dp)
                ) {
                    item(index, it)
                }
            }
        }
    }
}

/**
 * Adds a selectable list with custom items to the dialog
 *
 * @param list list of given generic type
 * @param onClick callback with the index and item when a list object is clicked
 * @param isEnabled a function to check if the item at a given index is enabled/clickable
 * @param item a composable function which takes an object of given generic type
 */
@Composable
fun <T> MaterialDialogScope.listItems(
    list: List<T>,
    closeOnClick: Boolean = true,
    label: (T) -> String,
    onClick: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    listItems(list = list, closeOnClick = closeOnClick, onClick = onClick) { _, item ->
        Text(
            label(item),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.Start)
        )
    }
}

/**
 * Adds a selectable plain text list to the dialog
 *
 * @param list the strings to be displayed in the list
 * @param onClick callback with the index and string of an item when it is clicked
 */
@Composable
fun MaterialDialogScope.listItems(
    list: List<String>,
    closeOnClick: Boolean = true,
    onClick: (index: Int, item: String) -> Unit = { _, _ -> }
) {
    listItems(list = list, closeOnClick = closeOnClick, onClick = onClick) { _, item ->
        Text(
            item,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.Start)
        )
    }
}

/**
 * Adds a multi-choice list view to the dialog
 * @param list a list of string labels for the multi-choice items
 * @param disabledIndices a list of indices which should be disabled/unselectable
 * @param initialSelection a list of indices which should be selected initially
 * @param waitForPositiveButton if true the [onCheckedChange] callback will only be called when the
 * positive button is pressed, otherwise it will be called when the a new item is selected
 * @param onCheckedChange a function which is called with a list of selected indices. The timing of
 * this call is dictated by [waitForPositiveButton]
 */
@Composable
fun MaterialDialogScope.listItemsMultiChoice(
    list: List<String>,
    disabledIndices: Set<Int> = setOf(),
    initialSelection: Set<Int> = setOf(),
    waitForPositiveButton: Boolean = true,
    onCheckedChange: (indices: Set<Int>) -> Unit = {}
) {
    var selectedItems by remember { mutableStateOf(initialSelection.toMutableSet()) }

    if (waitForPositiveButton) {
        DialogCallback { onCheckedChange(selectedItems) }
    }

    val onChecked = { index: Int ->
        if (index !in disabledIndices) {
            /* Have to create temp var as mutableState doesn't trigger on adding to set */
            val newSelectedItems = selectedItems.toMutableSet()
            if (index in selectedItems) {
                newSelectedItems.remove(index)
            } else {
                newSelectedItems.add(index)
            }
            selectedItems = newSelectedItems

            if (!waitForPositiveButton) {
                onCheckedChange(selectedItems)
            }
        }
    }

    val isEnabled = remember(disabledIndices) { { index: Int -> index !in disabledIndices } }

    listItems(
        list = list,
        onClick = { index, _ -> onChecked(index) },
        isEnabled = isEnabled,
        closeOnClick = false
    ) { index, item ->
        val enabled = remember(disabledIndices) { index !in disabledIndices }
        val selected = remember(selectedItems) { index in selectedItems }

        MultiChoiceItem(
            item = item,
            index = index,
            selected = selected,
            enabled = enabled,
            onChecked = onChecked
        )
    }
}

/**
 * Adds a single-choice list view to the dialog
 * @param list a list of string labels for the single-choice items
 * @param disabledIndices a list of indices which should be disabled/unselectable
 * @param initialSelection the index of the item that should initially be selected
 * @param waitForPositiveButton if true the [onChoiceChange] callback will only be called when the
 * positive button is pressed, otherwise it will be called when the a new item is selected
 * @param singleChoiceColor single choice radio button color, defaults to primary.
 * @param onChoiceChange a function which is called with the index of the selected item.
 * The timing of this call is dictated by [waitForPositiveButton]
 */
@Composable
fun MaterialDialogScope.listItemsSingleChoice(
    list: List<String>,
    disabledIndices: Set<Int> = setOf(),
    initialSelection: Int? = null,
    waitForPositiveButton: Boolean = true,
    singleChoiceColor: Color = MaterialTheme.colors.primary,
    onChoiceChange: (selected: Int) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf(initialSelection) }
    PositiveButtonEnabled(valid = selectedItem != null) {}

    if (waitForPositiveButton) {
        DialogCallback { onChoiceChange(selectedItem!!) }
    }

    val onSelect = { index: Int ->
        if (index !in disabledIndices) {
            selectedItem = index

            if (!waitForPositiveButton) {
                onChoiceChange(selectedItem!!)
            }
        }
    }

    val isEnabled = remember(disabledIndices) { { index: Int -> index !in disabledIndices } }
    listItems(
        list = list,
        closeOnClick = false,
        onClick = { index, _ -> onSelect(index) },
        isEnabled = isEnabled
    ) { index, item ->
        val enabled = remember(disabledIndices) { index !in disabledIndices }
        val selected = remember(selectedItem) { index == selectedItem }

        SingleChoiceItem(
            item = item,
            index = index,
            selected = selected,
            enabled = enabled,
            onSelect = onSelect,
            singleChoiceColor = singleChoiceColor
        )
    }
}

/**
 * Adds a single-choice with a custom view to the dialog
 * @param list a list of string labels for the single-choice items
 * @param disabledIndices a list of indices which should be disabled/unselectable
 * @param initialSelection the index of the item that should initially be selected
 * @param waitForPositiveButton if true the [onChoiceChange] callback will only be called when the
 * positive button is pressed, otherwise it will be called when the a new item is selected
 * @param singleChoiceColor single choice radio button color, defaults to primary.
 * @param onChoiceChange a function which is called with the index of the selected item.
 * The timing of this call is dictated by [waitForPositiveButton]
 * @param customView is the view that will be displayed for each [CustomSingleChoiceItem] in the list.
 */
@Composable
fun <T> MaterialDialogScope.customItemsSingleChoice(
    list: List<T>,
    disabledIndices: Set<Int> = setOf(),
    initialSelection: Int? = null,
    waitForPositiveButton: Boolean = true,
    singleChoiceColor: Color = MaterialTheme.colors.primary,
    onChoiceChange: (selected: Int) -> Unit = {},
    customView: @Composable (T) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf(initialSelection) }
    PositiveButtonEnabled(valid = selectedItem != null) {}

    if (waitForPositiveButton) {
        DialogCallback { onChoiceChange(selectedItem!!) }
    }

    val onSelect = { index: Int ->
        if (index !in disabledIndices) {
            selectedItem = index

            if (!waitForPositiveButton) {
                onChoiceChange(selectedItem!!)
            }
        }
    }

    val isEnabled = remember(disabledIndices) { { index: Int -> index !in disabledIndices } }
    listItems(
        list = list,
        closeOnClick = false,
        onClick = { index, _ -> onSelect(index) },
        isEnabled = isEnabled
    ) { index, item ->
        val enabled = remember(disabledIndices) { index !in disabledIndices }
        val selected = remember(selectedItem) { index == selectedItem }

        Column {
            CustomSingleChoiceItem(
                item = item,
                index = index,
                selected = selected,
                enabled = enabled,
                onSelect = onSelect,
                singleChoiceColor = singleChoiceColor,
                view = customView
            )
            Divider(Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun MultiChoiceItem(
    item: String,
    index: Int,
    selected: Boolean,
    enabled: Boolean,
    onChecked: (index: Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = selected, onCheckedChange = { onChecked(index) }, enabled = enabled)
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(32.dp)
        )
        Text(
            item,
            color = if (enabled) {
                MaterialTheme.colors.onSurface
            } else {
                MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
            },
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun SingleChoiceItem(
    item: String,
    index: Int,
    selected: Boolean,
    enabled: Boolean,
    onSelect: (index: Int) -> Unit,
    singleChoiceColor: Color = MaterialTheme.colors.primary
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.testTag(MATERIAL_DIALOG_SINGLE_CHOICE_ITEM_RADIO_BUTTON_TAG),
            selected = selected,
            onClick = {
                if (enabled) {
                    onSelect(index)
                }
            },
            enabled = enabled,
            colors = RadioButtonDefaults.colors(singleChoiceColor)
        )
        HorizontalSpacer(width = 16.dp)
        Text(
            item,
            modifier = Modifier.testTag(MATERIAL_DIALOG_SINGLE_CHOICE_ITEM_TEXT_TAG),
            color = if (enabled) {
                MaterialTheme.colors.onSurface
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
            },
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun <T> CustomSingleChoiceItem(
    item: T,
    index: Int,
    selected: Boolean,
    enabled: Boolean,
    onSelect: (index: Int) -> Unit,
    singleChoiceColor: Color = MaterialTheme.colors.primary,
    view: @Composable (T) -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        RadioButton(
            modifier = Modifier.testTag(MATERIAL_DIALOG_SINGLE_CHOICE_ITEM_RADIO_BUTTON_TAG),
            selected = selected,
            onClick = {
                if (enabled) {
                    onSelect(index)
                }
            },
            enabled = enabled,
            colors = RadioButtonDefaults.colors(singleChoiceColor)
        )
        Spacer(
            modifier = Modifier
                .width(16.dp)
        )
        view(item)
    }
}

const val MATERIAL_DIALOG_SINGLE_CHOICE_ITEM_RADIO_BUTTON_TAG = "materialDialogSingleChoiceItemRadioButton"
const val MATERIAL_DIALOG_SINGLE_CHOICE_ITEM_TEXT_TAG = "materialDialogSingleChoiceItemText"
